package com.yc.arithmetic.unionfind;

/**
 * 基于树的深度选择深度深的那个作为根合并后其深度不变
 */
public class UnionFind4 extends AUnionFind{

    private final int size;
    private final int[] parent;
    private final int[] height;

    public UnionFind4(int size){
        this.size = size;
        parent = new int[size];
        height = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            height[i] = 1;
        }
    }

    @Override
    public int find(int element) {
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
        if(first == second){
            return;
        }
        if(height[first] > height[second]){
            parent[second] = first;
        }else if(height[first] < height[second]){
            parent[first] = second;
        }else {
            parent[first] = second;
            height[second] += 1;
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
        UnionFind4 unionFind = new UnionFind4(10);
        unionFind.test(unionFind);
    }
}
