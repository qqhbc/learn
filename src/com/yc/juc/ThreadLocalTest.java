package com.yc.juc;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 运行的当前线程持有 ThreadLocal.ThreadLocalMap，ThreadLocalMap底层存储是Entry(<ThreadLocal<?>, Object)</br>
 * 1. 假设线程存在线程池不会被回收，即使ThreadLocal被判断可以被回收，但是如果entry中的key是强引用，它指向了threadLocal，不可能被回收
 * 当前线程持有对ThreadLocalMap的引用，ThreadLocalMap持有entity[]的引用
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static final LinkedBlockingQueue<ThreadLocal<?>> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {
        threadLocal.remove();
        Thread thread = new Thread(() -> {
            //List<ThreadLocal<?>> list = new ArrayList<>();
            threadLocal.set("hty");
            threadLocal.remove();
            //list.add(threadLocal);
            threadLocal = null;
            try {
                ThreadLocal<?> take = queue.take();
                take.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        IntStream.rangeClosed(1, 10).forEach(System.out::println);
        System.out.println(thread.isAlive());
        TimeUnit.MILLISECONDS.sleep(10000);
        //thread.interrupt();
        System.out.println(thread.isAlive());
        TimeUnit.MILLISECONDS.sleep(Long.MAX_VALUE);
    }
}
