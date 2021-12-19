package com.yc.design.singleton;

public class Singleton {

    private static final Singleton singleton = new Singleton();

    private Singleton(){}

    public static Singleton getInstance() {
        return singleton;
    }

    public void test() {
        System.out.println("hello world");
    }

}

class SingletonTest {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.test();
    }
}

