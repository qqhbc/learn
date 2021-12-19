package com.yc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    private int value = 10;
    private int count;

    public synchronized int getValue() {
        System.out.println("get");
        return this.value;
    }

    public synchronized void setValue(int value) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.value = value;
    }

    public static void main(String[] args) {

        ThreadTest threadTest = new ThreadTest();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                System.out.println("get start");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadTest.getValue());
            System.out.println("xxx end");
        }).start();
        new Thread(() -> {
            System.out.println("set start");
            threadTest.setValue(20);
            System.out.println("set end");
        }).start();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.shutdown();
        executorService.shutdownNow();
    }
}
