package com.yc.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OOMObjectTest {
    static class OOMObject {
        // 64kb 大小
        private byte[] size = new byte[64 * 1024];
    }

    private static List<OOMObject> list = new ArrayList<>();

    public static void fillHeap(int count, int interval) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < count; i++) {
            TimeUnit.MILLISECONDS.sleep(interval);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        fillHeap(1000, 50);
    }
}
