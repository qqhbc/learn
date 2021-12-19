package com.yc.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ForkJoinPoolDemo {
    private static int count;
    public static Map<Integer, Integer> getData(int capacity){
        Map<Integer, Integer> map = new HashMap<>(capacity*3 / 4);
        int start = 10000 * count++;
        for (int i = 0; i < capacity; i++) {
            map.put(start + i + 1, i % 5);
        }
        return map;
    }

    public static void main(String[] args) throws Exception{
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(getData(900));
        ExecutorService service = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));
        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                int gap = 1000 - map.size();
                if(gap > 0) {
                    System.out.println("gap size: " + gap);
                    map.putAll(getData(gap));
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println(map.size());
        service.shutdown();
    }
}
