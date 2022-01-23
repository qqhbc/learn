package com.yc.design.observer;

import java.util.Vector;

abstract class Subject {
    private final Vector<Observer> vector = new Vector<>();
    protected final String context;

    public Subject(String context) {
        this.context = context;
    }

    public void addObserver(Observer observer) {
        this.vector.add(observer);
    }

    public void deleteObserver(Observer observer) {
        this.vector.remove(observer);
    }

    public void notifyObservers(String context) {
        for (Observer observer : vector) {
            observer.update(this, context);
        }
    }

    public abstract void doSomething();
}

class ConcreteSubject extends Subject {

    public ConcreteSubject(String context) {
        super(context);
    }

    @Override
    public void doSomething() {
        System.out.println(super.context + " ConcreteSubject doSomething");
        super.notifyObservers(super.context);
    }
}

interface Observer {
    void update(Subject subject, Object arg);
}
public class ConcreteObserver implements Observer {
    @Override
    public void update(Subject subject, Object arg) {
        System.out.println("ConcreteObserver handler " + arg);
    }
}

class ConcreteObserver1 implements Observer {

    @Override
    public void update(Subject subject, Object arg) {
        System.out.println("ConcreteObserver1 handler " + arg);
    }
}

class Client {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject("hello");
        subject.addObserver(new ConcreteObserver());
        subject.addObserver(new ConcreteObserver1());
        subject.doSomething();
    }
}
