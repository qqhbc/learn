package com.yc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class InheritableThreadLocalTest {
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws Exception {
        threadLocal.set(Thread.currentThread().getName() + "-val");
        //父子传递可以拿到
        new Thread(() -> {
            String s = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + " >> " + s);
        }, "child").start();

        ExecutorService service = Executors.newFixedThreadPool(1, new ThreadFactory() {
            final AtomicInteger atomicInteger = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "pool-threadLocal-test-" + atomicInteger.incrementAndGet());
            }
        });
        service.submit(() -> {
            String s = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + " >> " + s);
            threadLocal.remove();
        });
        TimeUnit.MILLISECONDS.sleep(100);
        threadLocal.set("hello-v1");
        service.submit(() -> {
            String s = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + " >> " + s);
        });
        service.shutdown();
    }
}
