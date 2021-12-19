package com.yc.juc.chapter14;

import java.util.concurrent.TimeUnit;

/**
 * 通过轮询和休眠将前提条件处理放在缓存容器中
 *
 * @param <V>
 * @see com.yc.juc.test.BoundedBuffer
 */
public class SleepyBounderBuffer<V> extends BaseBoundedBuffer<V> {
    public SleepyBounderBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            // 释放锁并休眠一段时间
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    System.out.println("成功取出数据啦");
                    return doTake();
                }
            }
            System.out.println("队列是空的，无法去除数据");
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SleepyBounderBuffer<String> bounderBuffer = new SleepyBounderBuffer<>(10);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                bounderBuffer.put("hello world");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(bounderBuffer.take());
    }
}
