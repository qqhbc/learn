package com.yc.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @see FutureTaskDemo
 * 通过闭锁实现测试n个任务并发执行的时间
 * 系统线程核(6)数(12 超线程)
 */
public class CountDownLatchDemo {
    public long getCurrentTime(int nThread, final Runnable task) throws InterruptedException {
        final CountDownLatch startGage = new CountDownLatch(1);
        final CountDownLatch endGage = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++) {
            Thread thread = new Thread(() -> {
                try {
                    startGage.await();
                    try {
                        task.run();
                    } finally {
                        endGage.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        TimeUnit.MILLISECONDS.sleep(2000);
        startGage.countDown();
        long l = System.nanoTime();
        endGage.await();
        return System.nanoTime() - l;
    }

    public long getTime(int nThread, final Runnable task) throws InterruptedException {
        final CountDownLatch endGage = new CountDownLatch(nThread);
        for (int i = 0; i < nThread; i++) {
            Thread thread = new Thread(() -> {
                try {
                    task.run();
                } finally {
                    endGage.countDown();
                }
            });
            thread.start();
        }
        long l = System.nanoTime();
        endGage.await();
        return System.nanoTime() - l;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();
        int num = 12;
        long currentTimeSum = countDownLatchDemo.getCurrentTime(num, () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long timeSum = countDownLatchDemo.getTime(num, () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(timeSum + " timeSum");
        System.out.println(currentTimeSum + " currentTimeSum");
    }
}
