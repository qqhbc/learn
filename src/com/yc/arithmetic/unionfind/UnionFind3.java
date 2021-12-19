package com.yc.arithmetic.unionfind;

/**
 * 基于权重选取合并根
 */
public class UnionFind3 extends AUnionFind{
    private final int size;
    private final int[] parent;
    private final int[] weight;

    public UnionFind3(int size){
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
        if(first == second) {
            return;
        }
        // 如果权重相同默认选用第二个作为根
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

    //0、1、2、3、4、5、6、7、8、9、
    //0、1、2、3、4、6、6、7、8、9、
    //0、2、2、3、4、6、6、7、8、9、
    //0、2、3、3、4、6、6、7、8、9、
    //0、2、3、4、4、6、6、7、8、9、
    //0、2、3、4、6、6、6、7、8、9、
    public static void main(String[] args) {
        UnionFind3 unionFind = new UnionFind3(10);
        unionFind.test(unionFind);
    }
}
