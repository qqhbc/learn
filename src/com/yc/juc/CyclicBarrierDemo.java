package com.yc.juc;

import java.text.MessageFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @see CountDownLatchDemo
 * 栅栏 与闭锁的区别在于，栅栏会让线程一直<strong>循环</strong>直到各个线程<strong>同时到达</strong>栅栏标记位<br>
 * 闭锁 只触发一次事件，栅栏可以多次重用\
 * <strong>栅栏构造函数可以传递一个Runnable,这个线程启动时逻辑决定其他线程执行次数</strong>
 */
class Board {
    void print() {
    }

    Board getBoard() {
        return this;
    }

    void setValues() {
        System.out.println(Thread.currentThread().getName() + " 正在设置呀 ");
    }

    void waitSplit() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("拆分结束啦！！");
    }

}

public class CyclicBarrierDemo {
    private final Board board;
    private final CyclicBarrier cyclicBarrier;
    private final Worker[] workers;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public CyclicBarrierDemo(Board board) {
        this.board = board;
        AtomicInteger num = new AtomicInteger();
        int count = Runtime.getRuntime().availableProcessors();
        cyclicBarrier = new CyclicBarrier(count, () -> {
            board.print();
            System.out.println(MessageFormat.format("开始执行{0}次线程都到达栅栏的场景啦", num.incrementAndGet()));
        });
        workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(board.getBoard());
        }
    }

    private class Worker implements Runnable {

        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                board.setValues();
                try {
                    cyclicBarrier.await();
                    // TimeUnit.MILLISECONDS.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "执行后续工作呀");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted ");
                    return;
                } catch (BrokenBarrierException e) {
                    System.out.println(Thread.currentThread().getName() + " brokenBarrier ");
                    return;
                }
            }
            System.out.println(Thread.currentThread().getName() + " 线程执行结束");
//            if(board.hasBaby()){
//                System.out.println("做一个标记嘻嘻嘻 " + cyclicBarrier.getNumberWaiting());
//                cyclicBarrier.reset();
//            }
        }
    }

    public void start() {
        for (Worker worker : workers) {
            executorService.execute(worker);
        }
        board.waitSplit();
    }

    public void end() {
        System.out.println("=======================================================");
        executorService.shutdownNow();
    }

    public static void main(String[] args) {
        Board board = new Board();
        CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo(board);
        cyclicBarrierDemo.start();
        new Thread(() -> {
            try {
                TimeUnit.NANOSECONDS.sleep(1 / 10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cyclicBarrierDemo.end();
        }).start();

        System.out.println("线程是不是阻塞啦");

    }
}
