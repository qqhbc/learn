package com.yc.design.visitor;

import java.util.Random;

interface Visitor {
    void visit(ConcreteElement concreteElement);
    void visit(ConcreteElement1 concreteElement1);
}

abstract class Element {
    public abstract void doSomething();

    public abstract void accept(Visitor visitor);
}

class ConcreteElement extends Element {

    @Override
    public void doSomething() {
        System.out.println("ConcreteElement doSomething");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class ConcreteElement1 extends Element {

    @Override
    public void doSomething() {
        System.out.println("ConcreteElement1 doSomething");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

public class ConcreteVisitor implements Visitor {
    @Override
    public void visit(ConcreteElement concreteElement) {
        concreteElement.doSomething();
    }

    @Override
    public void visit(ConcreteElement1 concreteElement1) {
        concreteElement1.doSomething();
    }
}

class ObjectStructure {
    private static final Random random = new Random();

    public static Element createElement() {
        if (random.nextBoolean()) {
            return new ConcreteElement();
        }
        return new ConcreteElement1();
    }
}

class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            ObjectStructure.createElement().accept(new ConcreteVisitor());
        }
    }
}
