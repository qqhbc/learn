package com.yc.design.component;

import java.util.ArrayList;
import java.util.List;

abstract class ComponentExtend1 {
    private final String name;
    private ComponentExtend1 parent;

    public ComponentExtend1(String name) {
        this.name = name;
    }

    public void doSomething() {
        System.out.println(this.name + " ComponentExtend1 doSomething");
    }

    public ComponentExtend1 getParent() {
        return parent;
    }

    public void setParent(ComponentExtend1 parent) {
        this.parent = parent;
    }

}

class LeafExtend1 extends ComponentExtend1 {

    public LeafExtend1(String name) {
        super(name);
    }
}

public class CompositeExtend1 extends ComponentExtend1 {
    private final List<ComponentExtend1> list = new ArrayList<>();

    public CompositeExtend1(String name) {
        super(name);
    }

    public void add(ComponentExtend1 componentExtend1) {
        this.list.add(componentExtend1);
    }

    public void remove(ComponentExtend1 componentExtend1) {
        this.list.remove(componentExtend1);
    }

    public List<ComponentExtend1> getChild() {
        return this.list;
    }
}

class ClientExtend1 {
    public static void main(String[] args) {
        CompositeExtend1 rootExtend = new CompositeExtend1("rootExtend-1");

        CompositeExtend1 leaderExtend1 = new CompositeExtend1("leaderExtend1-1");
        CompositeExtend1 leaderExtend2 = new CompositeExtend1("leaderExtend1-2");

        LeafExtend1 employeeExtend1 = new LeafExtend1("employeeExtend1-1-1");
        LeafExtend1 employeeExtend2 = new LeafExtend1("employeeExtend1-1-2");
        LeafExtend1 employeeExtend3 = new LeafExtend1("employeeExtend1-2-1");

        // 管家
        LeafExtend1 stewardExtend = new LeafExtend1("stewardExtend1");

        rootExtend.add(leaderExtend1);
        rootExtend.add(leaderExtend2);
        rootExtend.add(stewardExtend);

        leaderExtend1.add(employeeExtend1);
        leaderExtend1.add(employeeExtend2);

        leaderExtend2.add(employeeExtend3);

        rootExtend.doSomething();
        display(rootExtend);
    }

    public static void display(CompositeExtend1 compositeExtend1) {
        for (ComponentExtend1 componentExtend1 : compositeExtend1.getChild()) {
            componentExtend1.doSomething();
            if (!(componentExtend1 instanceof LeafExtend1)) {
                display((CompositeExtend1) componentExtend1);
            }
        }
    }

    public ComponentExtend1 find(LeafExtend1 leafExtend1) {
        // todo 懒得写
        return null;
    }
}
