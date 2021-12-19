package com.yc.juc.chapter14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于ReentrantLock实现Semaphore
 */
public class SemaphoreOnLock {
    private final Lock lock = new ReentrantLock();
    private final Condition permitAvailable = lock.newCondition();
    private int permits;

    public SemaphoreOnLock(int permits) {
        lock.lock();
        try {
            this.permits = permits;
        } finally {
            lock.unlock();
        }
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0) {
                permitAvailable.await();
            }
            --permits;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            ++permits;
            permitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        SemaphoreOnLock semaphoreOnLock = new SemaphoreOnLock(0);
        new Thread(() -> {
            System.out.println("尝试获取一个信号量");
            try {
                semaphoreOnLock.acquire();
                System.out.println("成功往下执行啦");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "acquire").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                semaphoreOnLock.release();
                System.out.println("成功释放一个信号量");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "release").start();
    }
}
