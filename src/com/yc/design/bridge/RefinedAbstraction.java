package com.yc.design.bridge;

abstract class Abstraction {
    private final Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public void request() {
        this.implementor.doSomething();
    }

    public Implementor getImplementor() {
        return this.implementor;
    }
}

interface Implementor {
    void doSomething();
    void doAnything();
}

class ConcreteImplementor implements Implementor {

    @Override
    public void doSomething() {
        System.out.println("ConcreteImplementor doSomething");
    }

    @Override
    public void doAnything() {
        System.out.println("ConcreteImplementor doAnything");
    }
}

class ConcreteImplementor1 implements Implementor {

    @Override
    public void doSomething() {
        System.out.println("ConcreteImplementor1 doSomething");
    }

    @Override
    public void doAnything() {
        System.out.println("ConcreteImplementor1 doAnything");
    }
}

public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    public void request() {
        super.request();
        super.getImplementor().doAnything();
    }
}

class Client {
    public static void main(String[] args) {
        Abstraction abstraction = new RefinedAbstraction(new ConcreteImplementor());
        abstraction.request();
        System.out.println("=====================================================");
        abstraction = new RefinedAbstraction(new ConcreteImplementor1());
        abstraction.request();
    }
}
