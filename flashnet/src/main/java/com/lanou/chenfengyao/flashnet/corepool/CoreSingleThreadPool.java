package com.lanou.chenfengyao.flashnet.corepool;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ChenFengYao on 16/5/11.
 * 核心的线程池的单例类
 */
public class CoreSingleThreadPool extends ThreadPoolExecutor {
    private volatile Semaphore semaphore;
    private List<Runnable> runnableList;
    private LoopThread loopThread;
    private boolean flag;
    private OutWay outWay;
    private static CoreSingleThreadPool coreSingleThreadPool
            = new CoreSingleThreadPool(Runtime.getRuntime().availableProcessors()+1);

    public static CoreSingleThreadPool getInstance(){
        return coreSingleThreadPool;
    }

    private CoreSingleThreadPool(int corePoolSize) {
        super(corePoolSize, corePoolSize, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        semaphore = new Semaphore(corePoolSize);
        runnableList = new LinkedList<>();
        flag = true;
        outWay = OutWay.FIFO;//默认是先进先出
        loopThread = new LoopThread();
        loopThread.start();
    }

    //提交任务的方法
    @Override
    public synchronized void execute(Runnable command) {
        //所有来的任务是提交到我们自己的任务队列中
        runnableList.add(command);
        if (runnableList.size() < 2) {
            //如果这是队列中的第一个任务,那么就去唤醒轮询线程
            synchronized (loopThread) {
                loopThread.notify();
            }

        }
    }

    //设置是FIFO/LIFO
    public void setWay(OutWay outWay) {
        this.outWay = outWay;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        //任务完成释放信号量
        semaphore.release();
    }

    @Override
    protected void terminated() {
        super.terminated();
        flag = false;//轮询线程关闭
    }

    class LoopThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (flag) {
                if (runnableList.size() == 0) {
                    try {
                        //如果没有任务,轮询线程就等待
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        //请求信号量
                        semaphore.acquire();
                        int index = runnableList.size();
                        switch (outWay) {
                            case FIFO:
                                //先进先出
                                index = 0;
                                break;
                            case LIFO:
                                //先进后出
                                index = runnableList.size() - 1;
                                break;
                        }
                        //调用父类的添加方法,将任务添加到线程池中
                        CoreSingleThreadPool.super.execute(runnableList.get(index));
                        runnableList.remove(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
