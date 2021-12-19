package com.yc.juc.application.vehicle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

class Point{
    private final int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

/**
 * 由于<strong>Point</strong>不可变对象，可以直接返回</br>
 * 实时展示车辆位置
 */
public class DelegatingVehicleTracker {
    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points){
        locations = new ConcurrentHashMap<>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    /**
     * 实时发生位置变化，因为返回的是相同的对象
     */
    public Map<String, Point> getLocations(){
        return getLocations(true);
    }

    public Map<String, Point> getLocations(boolean realTime){
        if (realTime) {
            return unmodifiableMap;
        }
        // 返回一个不会变化的车辆视图
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    public Point getLocation(String id){
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y){
        if (locations.replace(id, new Point(x, y)) == null){
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }
    }

    public static void main(String[] args) throws Exception{
        Map<String, Point> map = new HashMap<>();
        map.put("1", new Point(1, 1));
        map.put("2", new Point(2, 2));
        map.put("3", new Point(3, 3));
        map.put("4", new Point(4, 4));
        map.put("5", new Point(5, 5));
        DelegatingVehicleTracker delegatingVehicleTracker = new DelegatingVehicleTracker(map);
        System.out.println(delegatingVehicleTracker.getLocation("2"));
        new Thread(() -> {
            Map<String, Point> locations = delegatingVehicleTracker.getLocations(false);
            System.out.println(locations);
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("old: " + locations);
                System.out.println("new: " + delegatingVehicleTracker.getLocations());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(500);
        delegatingVehicleTracker.setLocation("3", 33,77);
    }
}
