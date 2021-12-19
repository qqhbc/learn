package com.yc.juc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 车辆追踪 java监视器模式
 */
class MutablePoint {
    private int x;
    private int y;

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

/**
 * 通过一个追踪管理器来保证可变对象的线程安全行
 * 即通过复制可变对象数据
 */
public class MonitorVehicleTracker {
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> locations() {
        return deepCopy(locations);
    }

    /**
     * 确保调用者不会获得可变的对象，从而造成线程不安全
     *
     * @param id
     * @return
     */
    public synchronized MutablePoint getLocation(String id) {
        MutablePoint mutablePoint = locations.get(id);
        return mutablePoint == null ? null : new MutablePoint(mutablePoint);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint mutablePoint = locations.get(id);
        if (Objects.isNull(mutablePoint)) {
            throw new RuntimeException("id not exist");
        }
        mutablePoint.setX(x);
        mutablePoint.setY(y);
    }

    /**
     * 通过 unmodifiableMap确保每次复制的point对象本身不会被修改
     *
     * @param locations
     * @return
     */
    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> locations) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : locations.keySet()) {
            result.put(id, new MutablePoint(locations.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }
}
