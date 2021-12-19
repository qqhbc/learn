package com.yc.juc.application.vehicle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 通过线程安全的<strong>Point</strong>, 还可以实时修改车辆位置
 */
class SafePoint{
    private int x,y;
    private SafePoint(int[] a){
        this(a[0], a[1]);
    }

    /**
     * 为啥不直接调用 this(p.x,p.y) 可能会出现竞态条件</br>
     * 其实我不太理解——可能是传入的p对象，可能被其他线程修改，毕竟要修改两个状态变量
     * @param p
     */
    public SafePoint(SafePoint p){
        this(p.get());
    }

    public SafePoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get(){
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "SafePoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
public class PublishingVehicleTracker {
    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations){
        this.locations = new ConcurrentHashMap<>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    public Map<String, SafePoint> getLocations(){
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id){
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y){
        if(!locations.containsKey(id)){
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }
        locations.get(id).set(x, y);
    }

    public static void main(String[] args) throws Exception {
        Map<String, SafePoint> map = new HashMap<>();
        map.put("1", new SafePoint(1, 1));
        map.put("2", new SafePoint(2, 2));
        map.put("3", new SafePoint(3, 3));
        map.put("4", new SafePoint(4, 4));
        map.put("5", new SafePoint(5, 5));
        PublishingVehicleTracker publishingVehicleTracker = new PublishingVehicleTracker(map);
        System.out.println(publishingVehicleTracker.getLocation("2"));
        new Thread(() -> {
            Map<String, SafePoint> locations = publishingVehicleTracker.getLocations();
            System.out.println(locations);
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(locations);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(500);
        publishingVehicleTracker.setLocation("3", 33, 88);


    }
}
