package com.yc.juc.chapter16;

import java.util.Objects;

/**
 * 验证可能存在指令重排序
 */
public class PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        Thread one;
        Thread other;
        while (true) {
            one = new Thread(() -> {
                a = 1;
                x = b;
            });
            other = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            other.start();
            one.join();
            other.join();
            if (Objects.equals(1, x) && Objects.equals(0, y)) {
                break;
            }
            i++;
        }

        System.out.println("x = " + x + ", y = " + y + "   " + i);
    }
}
