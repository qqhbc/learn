package com.yc.design.memento;

import lombok.Data;

interface IMemento {

}

@Data
class PermissionOriginator {
    private String state;

    public void changeState(String state) {
        this.state = state;
    }

    public IMemento createPermissionMemento() {
        return new PermissionMemento(this.state);
    }

    public void restorePermissionMemento(IMemento memento) {
        this.setState(((PermissionMemento) memento).getState());
    }

}

@Data
public class PermissionMemento implements IMemento {
    private String state;

    public PermissionMemento(String state) {
        this.state = state;
    }
}

@Data
class PermissionCaretaker {
    private IMemento memento;
}

class PermissionClient {
    public static void main(String[] args) {
        PermissionOriginator originator = new PermissionOriginator();
        originator.setState("初始化状态。。。");
        System.out.println(originator.getState());

        PermissionCaretaker caretaker = new PermissionCaretaker();
        caretaker.setMemento(originator.createPermissionMemento());

        System.out.println("================================================");
        originator.changeState("变更状态。。。");
        System.out.println(originator.getState());

        System.out.println("================================================");
        originator.restorePermissionMemento(caretaker.getMemento());
        System.out.println(originator.getState());

    }
}
