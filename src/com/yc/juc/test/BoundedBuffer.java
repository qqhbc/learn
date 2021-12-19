package com.yc.juc.test;

import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓冲
 *
 * @param <E>
 */
public class BoundedBuffer<E> {
    private final Semaphore availableItems, availableSpace;
    private final E[] items;
    private int putPosition = 0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        // 可以从缓存中删除元素的个数,初始化时缓存中不存在元素，此时take调用acquire()就会阻塞
        availableItems = new Semaphore(0);
        // 可以插入到缓存的元素个数
        availableSpace = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpace.availablePermits() == 0;
    }

    public void put(E e) throws Exception {
        // 消耗一个许可
        availableSpace.acquire();
        doInsert(e);
        // 创建一个许可
        availableItems.release();
    }

    public E take() throws Exception {
        availableItems.acquire();
        E item = doRemove();
        availableSpace.release();
        return item;
    }

    private synchronized void doInsert(E e) {
        int i = putPosition;
        items[i] = e;
        // 反复循环
        putPosition = Objects.equals(++i, items.length) ? 0 : i;
    }

    private synchronized E doRemove() {
        int i = takePosition;
        E item = items[i];
        items[i] = null;
        takePosition = Objects.equals(++i, items.length) ? 0 : i;
        return item;
    }

}
