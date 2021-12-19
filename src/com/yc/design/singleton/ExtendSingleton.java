package com.yc.design.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExtendSingleton {
    private static int maxNumOfSingleton = 2;

    private String name;

    private static int indexSingleton;

    private static final List<String> nameList = new ArrayList<>();

    private static final List<ExtendSingleton> singletonList = new ArrayList<>();

    static {
        for (int i = 0; i < maxNumOfSingleton; i++) {
            singletonList.add(new ExtendSingleton("name-" + i));
        }
    }

    private ExtendSingleton() {}

    private ExtendSingleton(String name) {
        nameList.add(name);
    }

    public static ExtendSingleton getInstance() {
        Random random = new Random();
        indexSingleton = random.nextInt(maxNumOfSingleton);
        return singletonList.get(indexSingleton);
    }

    public void test() {
        System.out.println(nameList.get(indexSingleton));
    }
}

class ExtendSingletonTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ExtendSingleton instance = ExtendSingleton.getInstance();
            System.out.print("test : ");
            instance.test();
        }
    }
}

