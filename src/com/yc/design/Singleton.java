package com.yc.design;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Singleton {
    private int i;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    // 不加volatile出现DCL单例指令重排序问题
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (Singleton.class) {
                if (Objects.isNull(instance)) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                instance.i += 1;
                instance.atomicInteger.incrementAndGet();
                System.out.println("i = " + instance.i + "  atomicInteger = " + instance.atomicInteger.get() + " " + Thread.currentThread().getName());
            }, "Thread - one").start();
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println("i = " + instance.i + "  atomicInteger = " + instance.atomicInteger.get());
    }
}
