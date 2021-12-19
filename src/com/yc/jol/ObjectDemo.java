package com.yc.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class ObjectDemo {
    public static void main(String[] args) throws Exception{
        Object obj = new Object();
         new Thread(() -> {
            System.out.println("before lock " + Thread.currentThread().getId());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj){
                System.out.println("locking " + Thread.currentThread().getId());
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
            System.out.println("after lock " + Thread.currentThread().getId());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }, "aaa").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("xxx before lock " + Thread.currentThread().getId());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            synchronized (obj){
                System.out.println("xxx locking " + Thread.currentThread().getId());
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
            System.out.println("xxx after lock " + Thread.currentThread().getId());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }, "xxx").start();

        TimeUnit.SECONDS.sleep(10);
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                if(finalI == 0) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(finalI + " weight before lock " + Thread.currentThread().getId());
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
                synchronized (obj){
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(finalI + " weight locking " + Thread.currentThread().getId());
                    System.out.println(ClassLayout.parseInstance(obj).toPrintable());
                }
                System.out.println(finalI + " weight after lock " + Thread.currentThread().getId());
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }).start();
        }
    }

}
