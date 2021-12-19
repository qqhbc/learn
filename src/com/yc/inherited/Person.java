package com.yc.inherited;

/**
 * static方法不能被子类继承，default可以被子类继承
 */
class Animal {
    private String name;

    public void init(String name) {
        this.name = name;
    }

    protected void getName() {
        System.out.println("名字：" + name);
    }
}

class Cat extends Animal {
    @Override
    public void init(String name) {
        super.init(name);
        System.out.println("派生类名字：" + name);
    }

    public static void main(String[] args) {
        Animal animal = new Cat();
        animal.init("hty");
        animal.getName();
    }
}

public interface Person {
    static void say() {
        System.out.println("hello world!!");
    }

    default int age(int i) {
        return i;
    }

    default void test() {
        System.out.println("all sub class!!!");
    }
}
