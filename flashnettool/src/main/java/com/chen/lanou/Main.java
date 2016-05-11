package com.chen.lanou;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by ChenFengYao on 16/5/11.
 */
public class Main {

    private static Semaphore semaphore;

    public static void main(String[] args) {

        semaphore = new Semaphore(1);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.printf("开始等待");
                    semaphore.acquire();
                    System.out.printf("第二个");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.printf("进入了catch");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("等待结束");
                semaphore.release();
            }
        }).start();
    }
}
