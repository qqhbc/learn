package com.yc.juc;

import java.util.concurrent.*;

/**
 * 在专门的线程中中断任务
 */
public class TimeRunDemo {
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 由于执行线程内部的错误不会被外部执行线程所捕获
     * 现在用一个volatile修饰的成员变量将执行线程内部的错误保存起来
     * 在内部线程启动这个任务后，手动调用包装这个错误的方法再次抛出异常
     * V():  rethrow
     *
     * @param r       任务线程
     * @param timeout 过期时间
     * @param unit    单位
     * @throws InterruptedException
     */
    public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class ReThrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    System.out.println("timeRun 1");
                    r.run();// 休眠1秒
                } catch (Throwable t) {
                    System.out.println("timeRun catch 4");
                    this.t = t;
                }
            }

            void rethrow() {
                System.out.println("rethrow 5");
                if (t != null) throw ExceptionUtils.launderThrowable(t);
            }
        }
        ReThrowableTask task = new ReThrowableTask();
        final Thread thread = new Thread(task);
        thread.start();
        // 延迟timeout时间执行此线程中断
        executor.schedule(thread::interrupt, timeout, unit);
        /*加入join保证thread限执行，然后才执行主线程，确保可以捕获到异常*/
        thread.join(unit.toMillis(600));
        task.rethrow();
    }

    public static void timeRunFuture(Runnable r, long timeout, TimeUnit unit) {
        Future<?> future = executor.submit(r);
        try {
            future.get(timeout, unit);
        } catch (ExecutionException e) {
            throw ExceptionUtils.launderThrowable(e.getCause());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("超时啦 2");
            // 通过调用cancel将其取消这个任务，mayInterruptIfRunning设置为true时，当这个任务正在执行时可将其取消（中断后会被InterruptedException捕获）
            future.cancel(true);
        }
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
//        try {
//            TimeRunDemo.timeRun(() -> {
//                try {
//                    System.out.println("实际任务 2");
//                    TimeUnit.MILLISECONDS.sleep(1000);
//                } catch (InterruptedException e) {
//                    System.out.println("实际中断啦 3");
//                    throw ExceptionUtils.launderThrowable(e.getCause());
//                }
//                System.out.println("hello world");
//            }, 500, TimeUnit.MILLISECONDS);
//        }catch (Exception e){
//            TimeRunDemo.executor.shutdown();
//        }


        TimeRunDemo.timeRunFuture(() -> {
            try {
                System.out.println("执行提交任务 1 " + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("任务被取消啦 3");
                // 取消的任务中断被捕获，抛出InterruptedException,并调用interrupted()清除中断状态
                System.out.println(Thread.currentThread().isInterrupted() + " " + Thread.currentThread().getName());
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().isInterrupted());
                boolean interrupted = Thread.interrupted();
                System.out.println("上一次 " + interrupted + "这一次 " + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
            System.out.println("hello world 4");
        }, 500, TimeUnit.MILLISECONDS);
    }
}
