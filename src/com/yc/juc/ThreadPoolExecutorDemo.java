package com.yc.juc;

import com.sun.istack.internal.NotNull;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//  列举15个任务，假设每个任务执行时间为10S
public class ThreadPoolExecutorDemo {
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolExecutorDemo(){
        threadPoolExecutor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS
                , new LinkedBlockingQueue<>(1024), new ThreadFactory() {
            private final AtomicInteger atomicInteger = new AtomicInteger(0);
            @Override
            public Thread newThread(@NotNull  Runnable r) {
                if(r == null){
                    throw new IllegalArgumentException();
                }
                return new Thread(r, "test-thread-" + atomicInteger.incrementAndGet() + "-");
            }
        }, new ThreadPoolExecutor.AbortPolicy());
    }

    public ThreadPoolExecutorDemo(ThreadPoolExecutor threadPoolExecutor){
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public void executor(int n, int sleepTime){
        for (int i = 0; i < n; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    print("创建线程");
                    TimeUnit.SECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void print(String name){
        LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) threadPoolExecutor.getQueue();
        System.out.println(LocalDateTime.now() + ":" + Thread.currentThread().getName() + name + ": 核心线程数 " + threadPoolExecutor.getCorePoolSize() +
                " 最大线程数 " + threadPoolExecutor.getMaximumPoolSize() + " 活跃线程数 " + threadPoolExecutor.getActiveCount() +
                " 线程池活跃度 " + divide(threadPoolExecutor.getActiveCount(), threadPoolExecutor.getMaximumPoolSize()) +
                " 任务完成数 " + threadPoolExecutor.getCompletedTaskCount() + " 任务总数 " + threadPoolExecutor.getTaskCount() +
                " 队列大小 " + (queue.remainingCapacity() + queue.size()) +
                " 当前排队任务数 " + queue.size() + " 剩余队列大小 " + queue.remainingCapacity() +
                " 队列使用度 " + divide(queue.size(), queue.remainingCapacity() + queue.size())
        );
    }

    public String divide(int a, int b){
        return String.format("%1.2f%%", (float) a / b * 100);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutorDemo demo = new ThreadPoolExecutorDemo();
        demo.executor(15, 10);
        demo.print("改变之前");
//        demo.threadPoolExecutor.setCorePoolSize(10);
//        demo.threadPoolExecutor.setMaximumPoolSize(10);
//        demo.print("改变之后");
        Thread.currentThread().join();
        if(demo.threadPoolExecutor.getCompletedTaskCount() == 15){
            demo.print("线程执行完");
        }
        demo.threadPoolExecutor.shutdown();
    }
}
