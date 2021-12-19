package com.yc.juc.test;

import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 基本的测试单元，首先进行串行测试，调用它们的各个方法，验证后验条件和不变性条件
 */
public class BoundedBufferTest {

    @Test
    public void testIsEmptyWhenConstructed() {
        BoundedBuffer<String> boundedBuffer = new BoundedBuffer<>(10);
        assert Objects.equals(boundedBuffer.isFull(), false);
        assert Objects.equals(boundedBuffer.isEmpty(), true);
    }

    @Test
    public void testIsFullAfterPuts() throws Exception {
        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            boundedBuffer.put(i);
        }
        assert Objects.equals(true, boundedBuffer.isFull());
        assert Objects.equals(false, boundedBuffer.isEmpty());
    }

    @Test
    public void testTakeBlocksWhenEmpty() {
        BoundedBuffer<Integer> boundedBuffer = new BoundedBuffer<>(10);
        Thread taker = new Thread(() -> {
            try {
                boundedBuffer.take();
                System.out.println("阻塞失败啦！");
            } catch (InterruptedException ignore) {

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        taker.start();
        try {
            TimeUnit.SECONDS.sleep(2);
            // 中断线程
            taker.interrupt(); // 2s41ms
            //Thread.interrupted(); // 4s52ms  todo
            // 通过join来保证测试最终完成了，即使taker由于某个原因不能正确结束，通过有时间限制的join确保主线程正确结束
            taker.join(2 * 1000);
            assert Objects.equals(false, taker.isAlive());
        } catch (Exception e) {
            System.out.println("阻塞失败啦！");
        }
        System.out.println(System.identityHashCode(new Object()));
        System.out.println(System.identityHashCode(new Object()));
    }

    @Test
    public void testBounderBufferTimer() throws InterruptedException {
        int count = 100000;
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int cap = 10; cap <= 1000; cap *= 10) {
            System.out.println("Capacity " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                PutTakeTest takeTest = new PutTakeTest(cap, pairs, count, executorService);
                System.out.print("Pairs " + pairs + "\t");
                takeTest.test();
                System.out.print("\t");
                TimeUnit.SECONDS.sleep(1);
                takeTest.test();
                System.out.println();
                TimeUnit.SECONDS.sleep(1);
            }
        }
        executorService.shutdown();
    }
}
