package com.yc.juc;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 模拟线程池的工作线程
 */
public class WorkerThread implements Runnable{
    private final Thread worker;
    private Runnable task;
    private final ThreadFactory factory = r -> new Thread(r, "worker-");

    public WorkerThread(Runnable runnable){
        worker = getThread().newThread(this);
        task = runnable;
    }

    public void start(){
        worker.start();
    }

    private ThreadFactory getThread(){
        return factory;
    }

    public void interrupt(){
        worker.interrupt();
    }

    @Override
    public void run() {
        runWorker(this);
    }

    /*
     * 方便捕获提交的任务异常
     */
    private void runWorker(WorkerThread w){
        Runnable task = w.task;
        w.task = null;
        try {
            task.run();
        }catch (RuntimeException | Error e){
            System.out.println("任务出现运行时异常或错误啦 ！！ " + e.getLocalizedMessage());
            throw e;
        }catch (Throwable t){
            System.out.println("t");
            t.printStackTrace();
            throw new Error(t);
        }
    }

    public static void main(String[] args) {
        WorkerThread wt = new WorkerThread(() -> {
            System.out.println("开始执行该方法！");
            try {
                TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("线程中断啦！！" + e.getMessage());
                throw new RuntimeException("非法操作异常！");
            }
        });
        wt.start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                wt.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "interruptThread").start();
    }
}
