package com.yc.arithmetic.unionfind;

public abstract class AUnionFind implements IUnionFind {
    protected void  test(IUnionFind unionFind){
        unionFind.printf();
        unionFind.union(5, 6);
        unionFind.printf();
        unionFind.union(1, 2);
        unionFind.printf();
        unionFind.union(2, 3);
        unionFind.printf();
        unionFind.union(1, 4);
        unionFind.printf();
        unionFind.union(1, 5);
        unionFind.printf();
        System.out.println("1  6是否连接 " + unionFind.isConnect(1, 6));
        System.out.println("1  8是否连接 " + unionFind.isConnect(1, 8));
    }
}
