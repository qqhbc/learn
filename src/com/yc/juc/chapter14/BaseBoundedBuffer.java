package com.yc.juc.chapter14;

/**
 * 抽象的有界缓存
 */
public abstract class BaseBoundedBuffer<V> {
    private final V[] buffer;
    private int count;
    private int head;
    private int tail;

    protected BaseBoundedBuffer(int capacity) {
        buffer = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buffer[tail] = v;
        if (++tail == buffer.length) {
            tail = 0;
        }
        count++;
    }

    protected synchronized final V doTake() {
        V v = buffer[head];
        buffer[head] = null;
        if (++head == buffer.length) {
            head = 0;
        }
        count--;
        return v;
    }

    protected synchronized final boolean isEmpty() {
        return count == 0;
    }

    protected synchronized final boolean isFull() {
        return count == buffer.length;
    }

}
