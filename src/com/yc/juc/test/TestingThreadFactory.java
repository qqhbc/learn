package com.yc.juc.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 很好的体现了封装性(对默认线程工厂有封装了一次，获取线程创建次数)
 *
 * @see Executors#defaultThreadFactory()
 */
public class TestingThreadFactory implements ThreadFactory {
    public final AtomicInteger numCreated = new AtomicInteger(0);
    private final ThreadFactory threadFactory = Executors.defaultThreadFactory();


    @Override
    public Thread newThread(Runnable r) {
        numCreated.incrementAndGet();
        return threadFactory.newThread(r);
    }
}
