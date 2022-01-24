package com.yc.design.facade;

class ClassA {
    public void doSomethingA() {
        System.out.println("doSomethingA");
    }
}

class ClassB {
    public void doSomethingB() {
        System.out.println("doSomethingB");
    }
}

class ClassC {
    public void doSomethingC() {
        System.out.println("doSomethingC");
    }
}

public class Facade {
    private final ClassA classA = new ClassA();
    private final ClassB classB = new ClassB();
    private final ClassC classC = new ClassC();

    public void methodA () {
        this.classA.doSomethingA();
    }

    public void methodB () {
        this.classB.doSomethingB();
    }

    public void methodC () {
        this.classC.doSomethingC();
    }
}

class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.methodA();
        facade.methodB();
        facade.methodC();
    }
}
