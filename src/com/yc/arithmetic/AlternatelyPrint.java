package com.yc.arithmetic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class AlternatelyPrint {
    private volatile static int flag = 1;
    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        AtomicInteger j = new AtomicInteger(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (flag <= 100){
                    if (flag % 10 == Integer.parseInt(Thread.currentThread().getName())) {
                        System.out.println(flag);
                        flag++;
                    }
                }
            }, i + "").start();

        }
    }
}
