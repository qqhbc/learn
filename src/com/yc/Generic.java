package com.yc;

import java.util.ArrayList;
import java.util.List;

interface Fruit {
}

class Apply implements Fruit {
}

class Banana implements Fruit {
}

/**
 * print的泛型<Strong><? extends Fruit></Strong> 可以接受又其子类及自身的实例对象</br>
 * print的泛型<Strong>Fruit</Strong> 只能接受自身，其子类不能被被识别，因为其子类有多个，编译器并不知道是哪一个
 */
public class Generic {
    public static void print(List<? extends Fruit> list) {
        for (Fruit fruit : list) {
            System.out.println(fruit);
        }
    }

    public static void main(String[] args) {
        List<Banana> list = new ArrayList<>();
        list.add(new Banana());
        print(list);
    }
}
