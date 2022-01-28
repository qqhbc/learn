package com.yc.design.memento;

import lombok.Data;

@Data
class Originator {
    private String state;

    public void changeState(String state) {
        this.state = state;
    }

    public Memento createMemento() {
        return new Memento(this.state);
    }

    public void restoreMemento(Memento memento) {
        this.setState(memento.getState());
    }

}

@Data
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }
}

@Data
class Caretaker {
    private Memento memento;
}

class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("初始状态。。。");
        System.out.println(originator.getState());

        Caretaker caretaker = new Caretaker();
        // 设置备忘录
        caretaker.setMemento(originator.createMemento());

        originator.changeState("修改状态。。。");
        System.out.println(originator.getState());

        // 恢复备忘录
        originator.restoreMemento(caretaker.getMemento());
        System.out.println(originator.getState());
    }
}