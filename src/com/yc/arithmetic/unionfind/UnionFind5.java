package com.yc.arithmetic.unionfind;

/**
 * 基于权重+压缩路径(进一步压缩深度，结点的父节点变更为父节点的父节点)
 */
public class UnionFind5 extends AUnionFind{
    private final int size;
    private final int[] parent;
    private final int[] weight;

    public UnionFind5(int size){
        this.size = size;
        parent = new int[size];
        weight = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            weight[i] = 1;
        }
    }
    @Override
    public int find(int element) {
        while (element != parent[element]){
            parent[element] = parent[parent[element]];
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
        int second = find(secondElement);
        int first = find(firstElement);
        if(first == second){
            return;
        }
        if(weight[first] > weight[second]){
            parent[second] = first;
            weight[first] += weight[second];
        }else {
            parent[first] = second;
            weight[second] += weight[first];
        }
    }

    @Override
    public void printf() {
        for (int i = 0; i < size; i++) {
            System.out.print(parent[i] + "、");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        UnionFind5 unionFind5 = new UnionFind5(10);
        unionFind5.test(unionFind5);
    }
}
