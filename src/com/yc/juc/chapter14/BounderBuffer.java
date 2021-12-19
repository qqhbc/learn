package com.yc.juc.chapter14;


import java.util.concurrent.TimeUnit;

/**
 * 使用条件队列来实现有界缓存</br>
 * 可以使用条件队列来实现的必然也可以通过轮询+休眠来实现</br>
 * <Strong>当线程遇到wait()时会被挂起，当其被唤醒时将与其他线程共同竞争条件队列锁，如果该线程获取锁时则回到挂起的点继续执行</Strong></br>
 * 由于每次被唤醒时，都会回到挂起的点继续执行（并不知道条件谓词是否为真），因此需要循环判断条件谓词是否为真来防止过早唤醒
 *
 * @param <V>
 * @see SleepyBounderBuffer
 */
public class BounderBuffer<V> extends BaseBoundedBuffer<V> {
    public BounderBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        // 这里可以优化一下子,放在doPut(v)前面，否则没有意义
        boolean wasEmpty = isEmpty();
        doPut(v);
        // 只有时空的情况下，条件队列才会存在线程取元素
        if (wasEmpty)
            notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            System.out.println("马上要等待啦" + Thread.currentThread().getName() + "0");
            wait();
            System.out.println("已经恢复啦" + Thread.currentThread().getName());
        }
        boolean isFull = isFull();
        System.out.println("会不会执行呢" + Thread.currentThread().getName());
        V take = doTake();
        if (isFull)
            notifyAll();
        return take;
    }

    public static void main(String[] args) throws InterruptedException {
        BounderBuffer<String> boundedBuffer = new BounderBuffer<>(10);
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    boundedBuffer.put("hello world " + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        new Thread(() -> {
            try {
                System.out.println(boundedBuffer.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "other").start();
        System.out.println(boundedBuffer.take());
        System.out.println("结束啦！！！");
    }
}
