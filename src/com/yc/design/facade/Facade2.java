package com.yc.design.facade;

/**
 * 访问C时必须先访问A
 * 子系统内部重新封装一个类
 * 门面对象不参与业务逻辑
 */
class Context {
    private final ClassA classA = new ClassA();
    private final ClassC classC = new ClassC();

    public void methodC() {
        System.out.println("=========================");
        this.classA.doSomethingA();
        this.classC.doSomethingC();
        System.out.println("=========================");
    }
}
public class Facade2 {
    private final ClassA classA = new ClassA();
    private final ClassB classB = new ClassB();
    private final Context context = new Context();

    public void methodA() {
        this.classA.doSomethingA();
    }

    public void methodB() {
        this.classB.doSomethingB();
    }

    public void methodC() {
        this.context.methodC();
    }
}

class Client2 {
    public static void main(String[] args) {
        Facade2 facade2 = new Facade2();
        facade2.methodA();
        facade2.methodB();
        facade2.methodC();
    }
}
