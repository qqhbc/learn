package com.yc.arithmetic.unionfind;

public class UnionFind2 extends AUnionFind{
    private final int size;
    private final int[] parent;

    public UnionFind2(int size){
        this.size = size;
        parent = new int[size];
        // 初始化
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int element){
        while (element != parent[element]){
            element = parent[element];
        }
        return element;
    }

    @Override
    public boolean isConnect(int firstElement, int secondElement) {
        return find(firstElement) == find(secondElement);
    }

    @Override
    public void union(int firstElement, int secondElement) {
        int first = find(firstElement);
        int second = find(secondElement);
        if(first != second){
            parent[first] = second;
        }
    }

    @Override
    public void printf(){
        for (int i = 0; i < size; i++) {
            System.out.print(parent[i] + "、");
        }
        System.out.println();
    }

    // 0 1 2 3 4 5 6 7 8 9

    public static void main(String[] args) {
        UnionFind2 unionFind = new UnionFind2(10);
        unionFind.test(unionFind);
        // 5 6
        // 0 1 2 3 4 6 6 7 8 9
        // 1 2
        // 0 2 2 3 4 6 6 7 8 9
        // 2 3
        // 0 2 3 3 4 6 6 7 8 9
        // 1 4
        // 0 2 3 4 4 6 6 7 8 9
        // 1 5
        // 0 2 3 4 6 6 6 7 8 9
    }

}
