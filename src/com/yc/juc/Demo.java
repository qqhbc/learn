package com.yc.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {
    private final Person person = new Person();

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            Demo demo = new Demo();
            demo.person.setAge(111);
            System.out.println(demo.person);
            Map<Integer, String> map = new HashMap<>(2);
            map.put(2, "hty");
            map.put(4, "ssl");
            System.out.println(map);
        } finally {
            lock.unlock();
        }
    }
}
