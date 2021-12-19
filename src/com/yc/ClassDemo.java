package com.yc;

import java.util.concurrent.atomic.AtomicStampedReference;

public class ClassDemo{
    private static final Object lock = new Object();
    public static void main(String[] args) {
        AtomicStampedReference<String> a = new AtomicStampedReference<>("hello", 1);
        boolean b = a.compareAndSet("hello", "world", 1, 10);
        System.out.println(b + " " + a.getReference());
    }

}
