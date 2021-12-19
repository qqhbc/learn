package com.yc.juc.test;

public class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    @Override
    public void run() {
        synchronized (this) {
            long l = System.nanoTime();
            if (!started) {
                started = true;
                startTime = l;
            } else {
                endTime = l;
            }
        }
    }

    public synchronized void clear() {
        this.started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }
}
