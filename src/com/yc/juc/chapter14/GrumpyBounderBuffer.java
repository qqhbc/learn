package com.yc.juc.chapter14;

import java.util.concurrent.TimeUnit;

/**
 * 将前提条件的失败传递给调用者，调用者需要处理异常并进行重试</br>
 * 导致一些功能无法实现，比如FIFO,因为调用者重试，失去了“谁先到达”的信息
 */

public class GrumpyBounderBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBounderBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException("队列已经满啦！");
        }
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty()) {
            throw new BufferEmptyException("队列是空的！");
        }
        return doTake();
    }

    private static class BufferFullException extends Exception {
        BufferFullException(String message) {
            super(message);
        }
    }

    private static class BufferEmptyException extends Exception {
        BufferEmptyException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GrumpyBounderBuffer<String> buffer = new GrumpyBounderBuffer<>(10);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("准备放入数据啦");
                buffer.put("hty");
            } catch (BufferFullException e) {
                e.printStackTrace();
            }
        }).start();

        String str;
        while (true) {
            try {
                str = buffer.take();
                System.out.println("做一些处理 ");
                TimeUnit.MILLISECONDS.sleep(200);
                break;
            } catch (BufferEmptyException e) {
                System.out.println(e.getMessage());
                TimeUnit.SECONDS.sleep(2);
            }
        }
        System.out.println("从队列中取出 " + str);
    }
}
