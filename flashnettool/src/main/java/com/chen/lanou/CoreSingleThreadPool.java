package com.chen.lanou;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ChenFengYao on 16/5/11.
 * 核心的线程池的单例类
 */
public class CoreSingleThreadPool {
    private static CoreSingleThreadPool ourInstance;
    private ExecutorService threadPool;//线程池

    public static CoreSingleThreadPool getInstance() {
        if (ourInstance == null) {
            synchronized (CoreSingleThreadPool.class) {
                if (ourInstance == null) {
                    ourInstance = new CoreSingleThreadPool();
                }
            }
        }

        return ourInstance;
    }

    private CoreSingleThreadPool() {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        threadPool = Executors.newFixedThreadPool(cpuCores);
    }
}
