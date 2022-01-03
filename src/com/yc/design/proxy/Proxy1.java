package com.yc.design.proxy;

interface Subject {
    void doSomething();
}
class RealSubject implements Subject {

    @Override
    public void doSomething() {
        System.out.println("doSomething()");
    }
}
public class Proxy1 implements Subject {
    private final Subject subject;

    public Proxy1(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void doSomething() {
        this.doBefore();
        subject.doSomething();
        this.doAfter();
    }

    private void doBefore() {
        System.out.println("do before");
    }

    private void doAfter() {
        System.out.println("do after");
    }

}
class Proxy1Test {
    public static void main(String[] args) {
        Subject subject = new RealSubject();
        Proxy1 proxy1 = new Proxy1(subject);
        proxy1.doSomething();
    }
}
