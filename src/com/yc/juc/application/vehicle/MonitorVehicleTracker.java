package com.yc.juc.application.vehicle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 这种方式是通过包装类来保证线程安全，本身Point并不是线程安全的
 */
class MutablePoint{
    int x,y;
    public MutablePoint(){}
    public MutablePoint(MutablePoint p){
        this.x = p.x;
        this.y = p.y;
    }
    public MutablePoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "MutablePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

/**
 * 复制副本，非实时，视情况而定</br>
 * 由于每次查询都需要复制，性能不是很好
 */
public class MonitorVehicleTracker {
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations){
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations(){
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint mutablePoint = locations.get(id);
        return mutablePoint == null ? null : new MutablePoint(mutablePoint);
    }

    public synchronized void setLocation(String id, int x, int y){
        MutablePoint mutablePoint = locations.get(id);
        if(mutablePoint == null){
            throw new IllegalArgumentException("No such ID " + id);
        }
        mutablePoint.x = x;
        mutablePoint.y = y;
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m){
        Map<String, MutablePoint> map = new HashMap<>();
        for (String id : m.keySet()) {
            map.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) throws Exception{
        Map<String, MutablePoint> map = new HashMap<>();
        map.put("1", new MutablePoint(1, 1));
        map.put("2", new MutablePoint(2, 2));
        map.put("3", new MutablePoint(3, 3));
        map.put("4", new MutablePoint(4, 4));
        MonitorVehicleTracker monitorVehicleTracker = new MonitorVehicleTracker(map);
        MutablePoint location = monitorVehicleTracker.getLocation("2");
        System.out.println(location);

        new Thread(() -> {
            Map<String, MutablePoint> locations = monitorVehicleTracker.getLocations();
            System.out.println(locations);
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("old: " + locations);
                System.out.println("new: " + monitorVehicleTracker.getLocations());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(500);
        monitorVehicleTracker.setLocation("3", 55, 53);
    }
}
