package com.yc.arithmetic.unionfind;

public class UnionFind extends AUnionFind{
    private final int size;
    private final int[] ids;

    public UnionFind(int size){
        ids = new int[size];
        this.size = size;
        // 进行初始化
        for (int i = 0; i < size; i++) {
            ids[i] = i;
        }
    }

    public int find(int element){
        return ids[element];
    }

    public boolean isConnect(int firstElement, int secondElement){
        return find(firstElement) == find(secondElement);
    }

    public void union(int firstElement, int secondElement){
        int first = find(firstElement);
        int second = find(secondElement);
        if(first != second){
            for (int i = 0; i < size; i++) {
                if(first == ids[i]){
                    ids[i] = second;
                }
            }
        }
    }

    public void printf(){
        for (int i = 0; i < size; i++) {
            System.out.print(ids[i] + "、");
        }
        System.out.println();
    }

    // 5 6   1 2   2 3  1 4  1 5
    // 0 6 6 6 6 6 6 7 8 9
    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(10);
        unionFind.test(unionFind);
    }
}

