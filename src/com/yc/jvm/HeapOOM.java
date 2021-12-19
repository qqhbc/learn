package com.yc.jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    private Object[] obj = new Object[32];
    private static final int _1MB = 1024 * 1024;

    static class ObjectOOM {
        private byte[] _10M = new byte[100 * 1024 * 1024];
    }

    public static void main(String[] args) {
        List<Byte[]> list = new ArrayList<>();
        System.out.println("总共内存：" + Runtime.getRuntime().totalMemory());
        System.out.println("空闲内存：" + Runtime.getRuntime().freeMemory());
        double[] doubles = new double[_1MB];
        System.out.println("空闲内存：" + Runtime.getRuntime().freeMemory());
        for (int i = 0; i < 32; i++) {
            if (i % 1 == 0) {
                list = new ArrayList<>();
            }
            list.add(new Byte[_1MB]);
        }
        System.out.println("空闲内存：" + Runtime.getRuntime().freeMemory());
    }
}
