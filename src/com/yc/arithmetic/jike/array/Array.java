package com.yc.arithmetic.jike.array;

/**
 * 保证数组数据连续
 */
public class Array {
    private int[] data;
    private int size;
    private int len;

    public Array(int capacity) {
        this.data = new int[capacity];
        this.size = 0;
        this.len = capacity;
    }

    public int find(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        return data[index];
    }

    public void set(int index, int val) {
        if (index > 0 && index < len) {
            data[index] = val;
        }
    }

    public boolean delete(int index) {
        if (index < 0 || index >= size) {
            return false;
        }
        // 要删除下标元素的后面所有元素都向前移动一格
        // 当i条件包含等于size时 该数组不存在末尾数据未清除情况
        for(int i=index+1;i<=size;i++) {
            data[i-1] = data[i];
        }
        size--;
        return true;
    }

    // 1 3 5  9 11
    public boolean insert(int index, int val) {
        if (size == len) {
            System.out.println("数据已满");
            return false;
        }
        if (index < 0 || index > size) {
            System.out.println("位置不合法");
            return false;
        }
        // 要插入下标元素的后面所有元素都向后移动一个
        for (int i = size; i > index ; i--) {
            data[i] = data[i - 1];
        }
        data[index] = val;
        size++;
        return true;
    }

    public void printAll() {
        for (int i=0;i<size;i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Array array = new Array(10);
        array.insert(0, 1);
        array.insert(1, 3);
        array.insert(2, 5);
        array.insert(3, 9);
        array.insert(4, 11);
        array.printAll();
        array.insert(3, 7);
        array.insert(6, 22);
        array.printAll();
        array.set(6, 15);
        array.printAll();
        array.delete(6);
        array.printAll();
        array.delete(3);
        array.printAll();
        System.out.println();
    }
}
