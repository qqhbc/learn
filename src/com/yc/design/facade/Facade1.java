package com.yc.design.facade;

/**
 * 外部只依赖ClassB
 * 重新封装一个门面对象
 */
public class Facade1 {
    private final Facade facade = new Facade();

    public void methodB() {
        this.facade.methodB();
    }
}

class Client1 {
    public static void main(String[] args) {
        Facade1 facade1 = new Facade1();
        facade1.methodB();
    }
}
