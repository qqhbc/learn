package com.yc.juc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 通过信号量实现将任意容器变为有界阻塞容器
 *
 * @param <T>
 */
public class SemaphoreDemo<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public SemaphoreDemo(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.semaphore = new Semaphore(bound);
    }

    public boolean add(T t) throws InterruptedException {
        semaphore.acquire();
        boolean add = false;
        try {
            add = set.add(t);
            return add;
        } finally {
            if (!add) {
                // 如果set添加失败，就释放许可，保证set容器大小始终不超过bound
                semaphore.release();
            }
        }
    }

    public boolean remove(T t) {
        boolean remove = set.remove(t);
        if (remove) {
            // set删除成功就释放许可，保证set容器大小始终不超过bound
            semaphore.release();
        }
        return remove;
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreDemo<String> semaphoreDemo = new SemaphoreDemo<>(10);
        for (int i = 0; i < 10; i++) {
            semaphoreDemo.add(i + "");
        }
        System.out.println("hello world");
    }
}
