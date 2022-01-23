package com.yc.design.observer;

import java.util.Observable;

interface SubjectExtend {
    void doSomething(String context);
}

class ConcreteSubjectExtend extends java.util.Observable implements SubjectExtend {

    @Override
    public void doSomething(String context) {
        System.out.println(context + "\tConcreteSubjectExtend doSomething");
        super.setChanged();
        super.notifyObservers(context);
    }
}

public class ConcreteObserverExtend implements java.util.Observer {
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ConcreteObserverExtend handler\t" + arg);
    }
}

class ConcreteObserverExtend1 implements java.util.Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ConcreteObserverExtend1 handler\t" + arg);
    }
}

class ClientExtend {
    public static void main(String[] args) {
        SubjectExtend subjectExtend = new ConcreteSubjectExtend();
        ((Observable) subjectExtend).addObserver(new ConcreteObserverExtend());
        ((Observable) subjectExtend).addObserver(new ConcreteObserverExtend1());
        subjectExtend.doSomething("hello");
    }
}
