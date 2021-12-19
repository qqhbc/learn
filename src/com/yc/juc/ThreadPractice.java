package com.yc.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 存在1000个元素，现在有5个线程，每10次一提交
 */
public class ThreadPractice {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition commit = lock.newCondition();
        Condition condition = lock.newCondition();
        boolean flag = false;
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1000);
        for (int i = 1; i <= 999; i++) {
            queue.offer(i + " 元素");
        }
        // 确保添加了1000个
        System.out.println(queue.size());
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Semaphore semaphore = new Semaphore(5);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    condition.await();
                    System.out.println("当作消费了");
                    commit.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "write");
        thread.start();
        for (int i = 0; i < 5; i++) {
            threadPool.execute(() -> {
                while (queue.peek() != null) {
                    try {
                        semaphore.acquire();
                        try {
                            lock.lock();
                            try {
                                if (atomicInteger.get() != 0 && atomicInteger.get() % 10 == 0) {
                                    System.out.println(" " + Thread.currentThread().getName() + " at " + atomicInteger.get());
                                    condition.signal();
                                    commit.await();
                                }
                                atomicInteger.getAndIncrement();

                            }finally {
                                lock.unlock();
                            }
                            String element = queue.take();
                            System.out.println("消费 " + Thread.currentThread().getName() + " " + element);

                        } finally {
                            semaphore.release();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                threadPool.shutdown();
            });
        }

    }
}
