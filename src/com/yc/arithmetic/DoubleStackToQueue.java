package com.yc.arithmetic;

import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 两栈实现队列
 */
public class DoubleStackToQueue {
    private final Stack<Integer> stack = new Stack<>();
    private final Stack<Integer> stack1 = new Stack<>();

    public void offer(Integer val) {
        stack.push(val);
    }

    public Integer poll() {
        if (stack1.isEmpty()) {
            while (!stack.isEmpty()) {
                stack1.push(stack.pop());
            }
        }
        if (stack1.isEmpty()) {
            throw new IllegalStateException("没有元素");
        }
        return stack1.pop();
    }

    public static void main(String[] args) {
        DoubleStackToQueue stackToQueue = new DoubleStackToQueue();
        stackToQueue.offer(2);
        stackToQueue.offer(3);
        stackToQueue.offer(4);
        stackToQueue.offer(5);

        System.out.println(stackToQueue.poll());
        System.out.println(stackToQueue.poll());
        System.out.println(stackToQueue.poll());
        stackToQueue.offer(66);
        stackToQueue.offer(166);
        System.out.println(stackToQueue.poll());
        System.out.println(stackToQueue.poll());
        System.out.println(stackToQueue.poll());

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> System.out.println("hello world"), 3, 3, TimeUnit.SECONDS);
    }
}
