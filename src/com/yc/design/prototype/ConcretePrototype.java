package com.yc.design.prototype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

interface Prototype {
    void doSomething();
}
public class ConcretePrototype implements Cloneable, Prototype{

    private String name;

    private ArrayList<String> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    protected ConcretePrototype clone() {
        ConcretePrototype concretePrototype = null;
        try {
            concretePrototype = (ConcretePrototype) super.clone();
            // 深拷贝
            concretePrototype.list = (ArrayList<String>) this.list.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return concretePrototype;
    }

    @Override
    public void doSomething() {
        System.out.println("name: " + this.name + "\tlist: " + this.list);
    }
}

class ConcretePrototypeTest {
    public static void main(String[] args) {
        ConcretePrototype concretePrototype = new ConcretePrototype();
        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        concretePrototype.setList(list);
        int i = 6;
        while (i < 10) {
            i++;
            ConcretePrototype clone = concretePrototype.clone();
            clone.setName("hty-" + i);
            List<String> copyList = Optional.ofNullable(clone.getList()).orElse(new ArrayList<>());
            copyList.add("YY-" + i);
            clone.doSomething();
        }
    }
}

