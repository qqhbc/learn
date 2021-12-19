package com.yc.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印一个字符串
 */
public class PrintTest {
    private final AtomicInteger count = new AtomicInteger(0);
    public void alternate(String str){
        ReentrantLock lock = new ReentrantLock();
        Condition digitCondition = lock.newCondition();
        Condition letterCondition = lock.newCondition();

        // 打印数字   a3b2
        new Thread(() -> {
            lock.lock();
            try{
                while (count.get() < str.length()) {
                    System.out.println("digit ------------> " + count.get());
                    try {
                        if (count.get() % 2 == 0) {
                            digitCondition.await();
                        }
                        char c = str.charAt(count.getAndIncrement());
                        System.out.println("digit " + c);
                        letterCondition.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
             finally {
                System.out.println("digit unlock");
                    lock.unlock();
                }
        }, "digit").start();

        // 打印字母
        new Thread(() -> {
            lock.lock();
            try {
                while (count.get() < str.length() -1) {
                    System.out.println("letter ---------------->"+ count.get());
                    try {
                        if (count.get() % 2 == 1) {
                            letterCondition.await();
                        }
                        char c = str.charAt(count.getAndIncrement());
                        System.out.println("letter " + c);
                        digitCondition.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                System.out.println("letter unlock");
                lock.unlock();
            }
        }, "letter").start();
    }
    public static void main(String[] args) {
        PrintTest test = new PrintTest();
        test.alternate("a3b2c6d8b9");
    }
}
