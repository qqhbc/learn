package com.yc.design.strategy;

interface IStrategy {
    void doSomething();
}

class Context {
    private final IStrategy strategy;

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void doAnything() {
        this.strategy.doSomething();
    }
}

public class ConcreteStrategy implements IStrategy {
    @Override
    public void doSomething() {
        System.out.println("ConcreteStrategy doSomething");
    }
}

class ConcreteStrategy1 implements IStrategy {

    @Override
    public void doSomething() {
        System.out.println("ConcreteStrategy1 doSomething");
    }
}

class Client {
    public static void main(String[] args) {
        IStrategy strategy = new ConcreteStrategy();
        Context context = new Context(strategy);
        context.doAnything();
        System.out.println("================================");
        strategy = new ConcreteStrategy1();
        Context context1 = new Context(strategy);
        context1.doAnything();
    }
}
