package com.yc.juc.test;

import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestingThreadFactoryTest {

    @Test
    public void testPoolExpansion() throws InterruptedException {
        int MAX_NUM = 10;
        TestingThreadFactory threadFactory = new TestingThreadFactory();
        // 重新设置线程名字及数量
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_NUM, threadFactory);
        for (int i = 0; i < 10 * MAX_NUM; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    // 默认工厂新建的线程而不是main线程
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (int i = 0; i < 20 && threadFactory.numCreated.get() <= MAX_NUM; i++) {
            System.out.println("xxxx");
            TimeUnit.MILLISECONDS.sleep(100);
        }
        assert Objects.equals(threadFactory.numCreated.get(), MAX_NUM);
        executorService.shutdownNow();
    }
}
