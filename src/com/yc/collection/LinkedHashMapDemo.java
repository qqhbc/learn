package com.yc.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class LinkedHashMapDemo {
    public Map<Integer, String> getMap(int len){
        Map<Integer, String> map = new HashMap<>((len / 3 * 4) + 1);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            map.put(i, String.valueOf((char) (65 + random.nextInt(26))));
        }
        return map;
    }

    public static void main(String[] args) {
        LinkedHashMapDemo demo = new LinkedHashMapDemo();
        Map<Integer, String> map = demo.getMap(10);
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put(2, "hello");
        linkedHashMap.putAll(map);
        System.out.println(linkedHashMap);
        for (int i = 0; i < 6; i++) {
            linkedHashMap.get(i);
        }
        System.out.println(linkedHashMap);
        linkedHashMap.get(0);
        System.out.println(linkedHashMap);
        Class<? extends LinkedHashMapDemo> aClass = demo.getClass();
    }
}
