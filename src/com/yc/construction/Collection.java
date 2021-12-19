package com.yc.construction;

public interface Collection<T> {
    void add(T t);

    void add(int index, T t);

    boolean remove(T t);

    T remove(int index);

    T find(int index);

    int findFirst(T t);

    String print();
}
