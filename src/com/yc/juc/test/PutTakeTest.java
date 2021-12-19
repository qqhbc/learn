package com.yc.juc.test;

import com.yc.util.RandomGeneratorUtils;

import java.util.Objects;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {
    private final ExecutorService pool;
    private final AtomicInteger putNum = new AtomicInteger(0);
    private final AtomicInteger takeNum = new AtomicInteger(0);
    private final BoundedBuffer<Integer> bb;
    private final CyclicBarrier cyclicBarrier;
    private final BarrierTimer timer;
    // 一对  审讯
    private final int nPairs, nTrials;

    public PutTakeTest(int capacity, int nPairs, int nTrials) {
        this(capacity, nPairs, nTrials, Executors.newCachedThreadPool());
    }

    public PutTakeTest(int capacity, int nPairs, int nTrials, ExecutorService pool) {
        this.pool = pool;
        this.timer = new BarrierTimer();
        this.bb = new BoundedBuffer<>(capacity);
        this.nPairs = nPairs;
        this.nTrials = nTrials;
        this.cyclicBarrier = new CyclicBarrier(2 * nPairs + 1, timer);
    }

    public void test() {
        long l = System.currentTimeMillis();
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            // 等待所有线程都创建完毕
            cyclicBarrier.await();
            // 等待所有线程都执行完毕
            cyclicBarrier.await();
            System.out.println("总耗时：" + (System.currentTimeMillis() - l) / 1000 + "  单位：秒");
            System.out.println("该操作所需要的时间：" + this.timer.getTime() / (nPairs * nTrials));
            this.timer.clear();
            assert Objects.equals(takeNum.get(), putNum.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PutTakeTest takeTest = new PutTakeTest(10, 10, 10000);
        takeTest.test();
        takeTest.pool.shutdown();
    }

    private class Producer implements Runnable {

        @Override
        public void run() {
            int seed = (this.hashCode() ^ (int) System.nanoTime());
            int sum = 0;
            try {
                cyclicBarrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = RandomGeneratorUtils.xorShift(seed);
                }
                putNum.getAndAdd(sum);
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeNum.getAndAdd(sum);
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
