package com.yc.design.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

interface Aggregate {
    void add(Object element);
    Iterator createIterator();
    boolean remove(Object element);
}

class ConcreteAggregate implements Aggregate {
    private final List<Object> list = new ArrayList<>();

    @Override
    public void add(Object element) {
        list.add(element);
    }

    @Override
    public Iterator createIterator() {
        return new ConcreteIterator(this.list);
    }

    @Override
    public boolean remove(Object element) {
        return this.list.remove(element);
    }
}

interface Iterator {
    Object next();
    boolean hashNext();
    void remove();
}

public class ConcreteIterator implements Iterator {
    private List<Object> list = new ArrayList<>();
    private int cursor = 0;
    private int lastRet = -1;

    public ConcreteIterator(List<Object> list) {
        this.list = list;
    }

    @Override
    public Object next() {
        Object result = null;
        int i = cursor;
        if (this.hashNext()) {
            result = list.get(lastRet = i);
            cursor = i + 1;
        }
        return result;
    }

    @Override
    public boolean hashNext() {
        return !Objects.equals(cursor, list.size()) ;
    }

    @Override
    public void remove() {
        this.list.remove(lastRet);
        cursor = lastRet;
        /// lastRet = -1;
    }
}

class Client {
    public static void main(String[] args) {
        Aggregate aggregate = new ConcreteAggregate();
        aggregate.add("hello");
        aggregate.add("ssss");
        aggregate.add("world");
        aggregate.add("abc");
        Iterator iterator = aggregate.createIterator();
        while (iterator.hashNext()) {
            Object result = iterator.next();
            if (Objects.equals("ssss", result)) {
                iterator.remove();
            } else {
                System.out.println(result);
            }
        }
//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        java.util.Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            if (Objects.equals("b", next)) {
//                iterator.remove();
//            } else {
//                System.out.println(next);
//            }
//        }

    }
}
