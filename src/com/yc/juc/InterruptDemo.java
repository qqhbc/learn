package com.yc.juc;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试中断时，被阻塞的线程正否正常退出
 * <strong>阻塞状态下中断会自动检查(自动挡)，而非阻塞状态下需手动判断(手动挡)</strong>
 */
class NoBlockInterrupt implements Runnable {
    @Override
    public void run() {
        int i = 0;
        while (true) {
            /*
             * 线程在非阻塞状态下中断时，需要手动检查该线程中断状态，以便取消任务
             */
            System.out.println("该线程还在继续 " + Thread.currentThread().isInterrupted());
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("会不会进入呢 " + Thread.interrupted());
                System.out.println("在执行一次 " + Thread.currentThread().isInterrupted());
                break;
            }
            System.out.println("非阻塞任务执行中 " + ++i);
        }
    }
}

public class InterruptDemo implements Runnable {
    private final BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(10);

    @Override
    public void run() {
        BigInteger one = BigInteger.ONE;
        while (true) {
            try {

                /*
                 * 阻塞时，该线程中断，会自动清除中断状态，并抛出中断异常
                 */
                queue.put(one = one.nextProbablePrime());
                System.out.println("将生产的素数放入队列中 " + one);

            } catch (InterruptedException e) {
                // InterruptedException,并调用interrupted()清除中断状态
                System.out.println("阻塞状态完成中断啦 " + Thread.currentThread().isInterrupted());
            }
        }
    }

    public BlockingQueue<BigInteger> getQueue() {
        return queue;
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptDemo interruptDemo = new InterruptDemo();
        Thread thread = new Thread(interruptDemo);
        thread.start();
        try {
            BigInteger take = interruptDemo.getQueue().take();
            System.out.println("去除一个任务啦 " + take);
            TimeUnit.SECONDS.sleep(2);
            thread.interrupt();
            System.out.println("将这个线程中断啦");
            BigInteger take1 = interruptDemo.getQueue().take();
            System.out.println("再一次去除一个任务啦 " + take1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("hello world");
        }

//        NoBlockInterrupt noBlockInterrupt = new NoBlockInterrupt();
//        Thread thread = new Thread(noBlockInterrupt);
//        thread.start();
//        try {
//            TimeUnit.MILLISECONDS.sleep(200);
//            thread.interrupt();
//            TimeUnit.MILLISECONDS.sleep(200);
//            thread.start();
//        } catch (InterruptedException e) {
//            System.out.println("当前线程：" + thread.isInterrupted());
//            System.out.println("Main线程：" + Thread.currentThread().isInterrupted());
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("hello world");
//        }

        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
