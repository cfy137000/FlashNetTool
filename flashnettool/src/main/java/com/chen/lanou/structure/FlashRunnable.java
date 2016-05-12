package com.chen.lanou.structure;

/**
 * Created by ChenFengYao on 16/5/12.
 * 每一个要使用的Runnable结构体
 * 抽象类,需要重写里面的doSth方法
 * 如果发现新的Uri在队列中存在,就把旧的干掉
 * 在网络请求前发现时间过去很长时间了,就不去网络请求
 */
public abstract class FlashRunnable implements Runnable{
    private Long time;
    private String url;

    //使用的时候需要输入时间,和网址
    public FlashRunnable(Long time,String url) {
        this.time = time;
        this.url = url;
    }

    @Override
    public void run() {
        doSth();
    }

    public abstract void doSth();
}
