package com.yc.juc.chapter14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 使用AQS实现二元闭锁
 * TODO 查看AQS源码
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private static class Sync extends AbstractQueuedSynchronizer {
        protected int tryAcquireShared(int ignored) {
            return (getState() == 1) ? 1 : -1;
        }

        protected boolean tryReleaseShared(int ignored) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) {
        OneShotLatch latch = new OneShotLatch();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println("开始执行啦 " + Thread.currentThread().getName());
                try {
                    latch.await();
                    System.out.println("已经执行结束啦 " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, i + "线程").start();
        }
        new Thread(() -> {
            System.out.println("准备唤起线程啦 " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.signal();
            try {
                TimeUnit.NANOSECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("已经唤起啦 " + Thread.currentThread().getName());
        }, "signal").start();
    }
}
