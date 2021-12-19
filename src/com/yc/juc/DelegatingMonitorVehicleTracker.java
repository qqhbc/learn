package com.yc.juc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 不可变对象， 基于委托的车辆追踪器
 */
class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

/**
 * 通过unmodifiableMap 对车辆位置进行复制，防止调用者对其进行操作
 */
public class DelegatingMonitorVehicleTracker {
    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingMonitorVehicleTracker(Map<String, Point> map) {
        locations = new ConcurrentHashMap<>(map);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    /**
     * 提供视图<strong>动态车辆变化视图</strong>
     * 实时反应车辆位置情况，对原locations引用其地址
     *
     * @return unmodifiableMap
     */
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    /**
     * 返回静态拷贝非实时拷贝
     * 通过构造hashMap实例确保返回车辆的位置一致，而非直接引用原locations对象地址
     *
     * @return
     */
    public Map<String, Point> getStaticLocations() {
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    /**
     * 采用替换实例对象，因为point对象时不可变的
     *
     * @param id
     * @param x
     * @param y
     */
    public void setLocation(String id, int x, int y) {
        locations.replace(id, new Point(x, y));
    }
}
