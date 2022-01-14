package com.yc.design.proxy;

import java.util.Objects;

public class Proxy2 implements Subject {
    private final Subject subject;

    public Proxy2(String name) {
        subject = new RealSubject2(this, name);
    }

    @Override
    public void doSomething() {
        this.doBefore();
        this.subject.doSomething();
        this.doAfter();
    }

    private void doBefore() {
        System.out.println("doBefore");
    }

    private void doAfter() {
        System.out.println("doAfter");
    }

    private static class RealSubject2 implements Subject {
        private final String name;

        public RealSubject2(Subject subject, String name) {
            if (Objects.isNull(subject)) {
                throw new IllegalArgumentException("代理类不能为空(不能创建真实角色)");
            }
            this.name = name;
        }

        @Override
        public void doSomething() {
            System.out.println(this.name + " doSomething");
        }
    }
}

class Proxy2Test {
    public static void main(String[] args) {
        Proxy2 proxy2 = new Proxy2("hello");
        proxy2.doSomething();
    }
}
