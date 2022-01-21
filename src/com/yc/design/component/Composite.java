package com.yc.design.component;

import java.util.ArrayList;
import java.util.List;

abstract class Component {
    private final String name;

    public Component(String name) {
        this.name = name;
    }

    public void doSomething() {
        System.out.println(this.name + " component doSomething");
    }
}

class Leaf extends Component {
    public Leaf(String name) {
        super(name);
    }
}
public class Composite extends Component {
    private final List<Component> list = new ArrayList<>();

    public Composite(String name) {
        super(name);
    }

    public void add(Component component) {
        list.add(component);
    }

    public void remove(Component component) {
        list.remove(component);
    }

    public List<Component> getChild() {
        return this.list;
    }
}

class Client {
    public static void main(String[] args) {
        Composite root = new Composite("root");

        Composite leader1 = new Composite("leader1");
        Composite leader2 = new Composite("leader2");

        Leaf employee1 = new Leaf("employee1-1");
        Leaf employee2 = new Leaf("employee1-2");
        Leaf employee3 = new Leaf("employee2-1");

        // 管家
        Leaf steward = new Leaf("steward");

        root.add(leader1);
        root.add(leader2);
        root.add(steward);

        leader1.add(employee1);
        leader1.add(employee2);

        leader2.add(employee3);

        root.doSomething();
        display(root);
    }

    public static void display(Composite composite) {
        for (Component component : composite.getChild()) {
            component.doSomething();
            if (!(component instanceof Leaf)) {
                display((Composite) component);
            }
        }
    }
}
