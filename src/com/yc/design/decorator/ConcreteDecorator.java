package com.yc.design.decorator;

abstract class Component {
    public abstract void operator();
}

class ConcreteComponent extends Component {

    @Override
    public void operator() {
        System.out.println("ConcreteComponent do Something! ");
    }
}

abstract class Decorator extends Component {
    private final Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operator() {
        this.component.operator();
    }
}

class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    private void method1() {
        System.out.println("method1 加强功能...");
    }

    @Override
    public void operator() {
        this.method1();
        super.operator();
    }
}

public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    private void method() {
        System.out.println("method 减弱功能...");
    }

    @Override
    public void operator() {
        this.method();
        super.operator();
    }
}

class Client {

    // 先减弱 再加强
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new ConcreteDecorator1(component);
        component = new ConcreteDecorator(component);
        component.operator();
    }
}
