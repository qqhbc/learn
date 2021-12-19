package com.yc.juc;

import java.util.concurrent.TimeUnit;

class MyThread extends Thread {
    public MyThread(Runnable r) {
        super(r);
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (Exception ignore) {
        }
        System.out.println("hello world!! 4");
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("hello 1 " + Thread.currentThread().getName());
    }
}

public class ThreadTest implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开始执行任务啦 3");
        throw new RuntimeException("这是一个错误");
    }

    public static void main(String[] args) {
//        new Thread(() -> {
//            new Thread(new ThreadTest(), "aabb").start();
//        }, "major").start();
//
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
//            System.out.println(t.getName() + "aaa" + e);
//        });
        Thread myThread = new MyThread(new ThreadTest());
        myThread.start();
        System.out.println("main 2");
    }
}
