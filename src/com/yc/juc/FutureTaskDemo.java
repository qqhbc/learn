package com.yc.juc;

import com.yc.MyException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 闭锁
 *
 * @see CountDownLatchDemo
 * 借助FutureTask完成将一个线程的计算结果传递到另一个线程
 * get()如果任务状态为终态(完成返回结果或抛出异常)，会立即响应，否则将调用者线程阻塞
 */
public class FutureTaskDemo {
    private final FutureTask<String> future = new FutureTask<>(() -> {
        System.out.println("进行复杂的计算");
        TimeUnit.SECONDS.sleep(5);
        //throw new IOException("俺要抛出一个受检查异常");
        return "success";
    });
    /*
     * 在构造函数或者静态初始化方法中启动线程不是一个很好的做法，因此提供了一个start方法来启动线程
     */
    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public void setCancel(boolean cancel) {
        future.cancel(cancel);
    }

    public String get() throws MyException, InterruptedException {
        try {
            return future.get();
            // 会抛出循行异常，受检查异常，错误
        } catch (ExecutionException e) {
            System.out.println("他出现了");
            Throwable cause = e.getCause();
            if (cause instanceof MyException) {
                throw (MyException) cause;
            } else if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new IllegalStateException("Not unchecked", cause);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, MyException {
        FutureTaskDemo futureTaskDemo = new FutureTaskDemo();
        futureTaskDemo.start();
        System.out.println("漫长的等待中");
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
                futureTaskDemo.setCancel(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        String s = futureTaskDemo.get();
        System.out.println(s);


    }
}
