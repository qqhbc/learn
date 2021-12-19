package com.yc.arithmetic.unionfind;

public interface IUnionFind {
    int find(int element);

    boolean isConnect(int firstElement, int secondElement);

    void union(int firstElement, int secondElement);

    void printf();
}
