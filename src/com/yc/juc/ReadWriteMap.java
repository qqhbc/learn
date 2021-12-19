package com.yc.juc;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 通过ReentrantReadWriteLock对Map进行封装，有效的提高并发</br>
 * 尽管ConcurrentHashMap已经对Map实现了很好的封装，但如果想对LinkedHashMap进行并发封装可以采用如下方式
 *
 * @param <K>
 * @param <V>
 * @see java.util.concurrent.locks.ReadWriteLock
 * @see java.util.concurrent.ConcurrentHashMap
 */
public class ReadWriteMap<K, V> {
    private final Map<K, V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 读读不冲突，读写、写写冲突
     */
    private final Lock read = lock.readLock();
    private final Lock write = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    /**
     * 相关的写操作，都可以采用写锁</br>
     * remove() clear() putAll()
     */
    public V put(K key, V value) {
        write.lock();
        try {
            return map.put(key, value);
        } finally {
            write.unlock();
        }
    }

    /**
     * 相关的读操作，都可以采用读锁</br>
     * size() isEmpty() containsKey() containsValue()
     */
    public V get(K key) {
        read.lock();
        try {
            return map.get(key);
        } finally {
            read.unlock();
        }
    }


}
