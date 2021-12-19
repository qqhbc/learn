package com.yc.juc.chapter14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于ReentrantLock 与 Condition 实现缓冲队列</br>
 * 保证不同队列条件上的条件谓词不相同
 *
 * @param <T>
 */
public class ConditionBoundedBuffer<T> extends BaseBoundedBuffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public ConditionBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (isFull()) {
                notFull.await();
            }
            boolean isEmpty = isEmpty();
            doPut(t);
            if (isEmpty)
                notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        T t;
        try {
            while (isEmpty()) {
                notEmpty.await();
            }
            boolean isFull = isFull();
            t = doTake();
            if (isFull)
                notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionBoundedBuffer<String> boundedBuffer = new ConditionBoundedBuffer<>(10);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                boundedBuffer.put("hello world");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "put").start();
        System.out.println("准备放入");
        System.out.println(boundedBuffer.take());
    }
}
