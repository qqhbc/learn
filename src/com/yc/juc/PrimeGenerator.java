package com.yc.juc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 素数生成任务
 */
public class PrimeGenerator implements Runnable {
    private final List<String> primes = new ArrayList<>();
    private volatile boolean cancel;

    @Override
    public void run() {
        // 保存在当先线程的局部变量表中，不共享
        BigInteger one = BigInteger.ONE;
        while (!cancel) {
            synchronized (this) {
                one = one.nextProbablePrime();
                primes.add(one + " " + Thread.currentThread().getName());
            }
        }
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public synchronized List<String> get() {
        return new ArrayList<>(primes);
    }

    public static void main(String[] args) {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        for (int i = 0; i < 2; i++) {
            new Thread(primeGenerator).start();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            primeGenerator.setCancel(true);
        }
        System.out.println(primeGenerator.get());
    }
}
