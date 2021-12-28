package com.yc.design.template_method;

abstract class AbstractClass {
    protected abstract void doSomething();
    protected abstract void doAnything();
    public void templateMethod() {
        doSomething();
        doAnything();
    }
}

public class ConcreteClass extends AbstractClass{
    @Override
    protected void doSomething() {
        System.out.println("hello doSomething");
    }

    @Override
    protected void doAnything() {
        System.out.println("hello doAnything");
    }
}

class ConcreteClassTest {
    public static void main(String[] args) {
        AbstractClass abstractClass = new ConcreteClass();
        abstractClass.templateMethod();
    }
}
