package com.yc.design.component;

import java.util.ArrayList;
import java.util.List;

abstract class ComponentExtend {
    private final String name;

    public ComponentExtend(String name) {
        this.name = name;
    }

    public void doSomething() {
        System.out.println(this.name + " ComponentExtend doSomething");
    }

    public abstract void add(ComponentExtend componentExtend);

    public abstract void remove(ComponentExtend componentExtend);

    public abstract List<ComponentExtend> getChild();
}

class LeafExtend extends ComponentExtend {

    public LeafExtend(String name) {
        super(name);
    }

    @Override
    @Deprecated
    public void add(ComponentExtend componentExtend) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void remove(ComponentExtend componentExtend) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public List<ComponentExtend> getChild() {
        throw new UnsupportedOperationException();
    }
}
public class CompositeExtend extends ComponentExtend {
    private List<ComponentExtend> list = new ArrayList<>();

    public CompositeExtend(String name) {
        super(name);
    }

    @Override
    public void add(ComponentExtend componentExtend) {
        this.list.add(componentExtend);
    }

    @Override
    public void remove(ComponentExtend componentExtend) {
        this.list.remove(componentExtend);
    }

    @Override
    public List<ComponentExtend> getChild() {
        return this.list;
    }
}

class ClientExtend {

    public static void main(String[] args) {
        ComponentExtend rootExtend = new CompositeExtend("rootExtend");

        ComponentExtend leaderExtend1 = new CompositeExtend("leaderExtend1");
        ComponentExtend leaderExtend2 = new CompositeExtend("leaderExtend2");

        ComponentExtend employeeExtend1 = new LeafExtend("employeeExtend1-1");
        ComponentExtend employeeExtend2 = new LeafExtend("employeeExtend1-2");
        ComponentExtend employeeExtend3 = new LeafExtend("employeeExtend2-1");

        // 管家
        ComponentExtend stewardExtend = new LeafExtend("stewardExtend");

        rootExtend.add(leaderExtend1);
        rootExtend.add(leaderExtend2);
        rootExtend.add(stewardExtend);

        leaderExtend1.add(employeeExtend1);
        leaderExtend1.add(employeeExtend2);

        leaderExtend2.add(employeeExtend3);

        rootExtend.doSomething();
        display(rootExtend);
    }

    public static void display(ComponentExtend componentExtend) {
        for (ComponentExtend component : componentExtend.getChild()) {
            component.doSomething();
            if (!(component instanceof LeafExtend)) {
                display(component);
            }
        }
    }

}
