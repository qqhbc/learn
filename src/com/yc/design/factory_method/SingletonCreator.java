package com.yc.design.factory_method;

import java.lang.reflect.Constructor;

class Singleton {
    private Singleton() {}

    public void doSomething() {
        System.out.println("hello Singleton and Factory");
    }
}
public class SingletonCreator {
    private static Singleton singleton;

    static {
        try {
            Class<?> aClass = Class.forName(Singleton.class.getName());
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            singleton = (Singleton) declaredConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Singleton getSingleton() {
        return singleton;
    }
}

class SingletonCreatorTest {
    public static void main(String[] args) {
        Singleton singleton = SingletonCreator.getSingleton();
        singleton.doSomething();
    }
}
