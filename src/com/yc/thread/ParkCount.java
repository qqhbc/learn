package com.yc.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Counter {
    private int count;

    public synchronized int increase() {
        // 不会释放锁
        Thread.yield();
        return ++count;
    }

    public synchronized int get() {
        return count;
    }
}

class CountHold {
    private static final Counter counter = new Counter();
    private static volatile boolean cancel;

    public static int increase() {
        return counter.increase();
    }

    public static int get() {
        return counter.get();
    }

    public static void setCancel() {
        cancel = true;
    }

    public static boolean getCancel() {
        return cancel;
    }
}

/**
 * 公园各个路口统计，每个路口可以看作是一个线程
 */
public class ParkCount implements Runnable {
    private static int count = 0;
    private final int id;
    private int number;
    private static final List<ParkCount> parkCounts = new ArrayList<>();

    public ParkCount() {
        id = count++;
        parkCounts.add(this);
    }

    public ParkCount(int id) {
        this.id = id;
        parkCounts.add(this);
    }

    @Override
    public void run() {
        while (!CountHold.getCancel()) {
            number++;
            System.out.println(this + " counter " + CountHold.increase());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("stopping total " + this);
    }

    public String toString(){
        return "parkCount-" + id + " count " + number;
    }

    public static int sum(){
        int sum = 0;
        for (ParkCount parkCount : parkCounts) {
            sum += parkCount.number;
        }
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            service.execute(new ParkCount());
        }
        TimeUnit.SECONDS.sleep(3);
        CountHold.setCancel();
        service.shutdown();
        System.out.println("parkCount sum " + ParkCount.sum());
        System.out.println("count total " + CountHold.get());
    }
}
