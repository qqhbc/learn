package com.yc.juc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程安全的可变对象
 */
class SafePoint {
    private int x;
    private int y;

    private SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    public SafePoint(SafePoint p) {
        this(p.get());
    }

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class publishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public publishingVehicleTracker(Map<String, SafePoint> map) {
        this.locations = new ConcurrentHashMap<>(map);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("illegal param id");
        }
        locations.get(id).set(x, y);
    }

    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        new Thread(() -> {
            synchronized (set) {
                for (int i = 0; i < 1000; i++) {
                    set.add(i);
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (set) {
                System.out.println(set);
            }
        }).start();
    }
}
