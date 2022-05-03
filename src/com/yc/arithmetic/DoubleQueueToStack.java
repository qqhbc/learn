package com.yc.arithmetic;

import java.util.ArrayDeque;
import java.util.Queue;

// 双队列实现栈
public class DoubleQueueToStack {
    private final Queue<Integer> queue = new ArrayDeque<>();
    private final Queue<Integer> queue1 = new ArrayDeque<>();

    public void push(Integer val) {
        if (queue.isEmpty() && queue1.isEmpty()) {
            queue.offer(val);
            return;
        }
        if (queue.isEmpty()) {
            queue1.offer(val);
            return;
        }
        if (queue1.isEmpty()) {
            queue.offer(val);
        }
    }

    public Integer pop() {
        if (queue.isEmpty() && queue1.isEmpty()) {
            throw new IllegalStateException("没有元素");
        }
        if (queue.isEmpty()) {
            while (queue1.size() > 1) {
                queue.offer(queue1.poll());
            }
            return queue1.poll();
        }
        if (queue1.isEmpty()) {
            while (queue.size() > 1) {
                queue1.offer(queue.poll());
            }
            return queue.poll();
        }
        return null;
    }

    public static void main(String[] args) {
        DoubleQueueToStack queueToStack = new DoubleQueueToStack();
        queueToStack.push(2);
        queueToStack.push(3);
        queueToStack.push(4);
        queueToStack.push(5);

        System.out.println(queueToStack.pop());
        System.out.println(queueToStack.pop());
        System.out.println(queueToStack.pop());
        queueToStack.push(188);
        queueToStack.push(44);
        System.out.println(queueToStack.pop());
        System.out.println(queueToStack.pop());
        System.out.println(queueToStack.pop());
        System.out.println(queueToStack.pop());
    }
}
