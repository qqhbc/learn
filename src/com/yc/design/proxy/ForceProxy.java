package com.yc.design.proxy;

interface ForceSubject {
    void doSomething();
    ForceSubject getProxy();
}

class ForceRealSubject implements ForceSubject {

    private ForceSubject proxy;

    @Override
    public void doSomething() {
        if (!isProxy()) {
            throw new IllegalStateException("必须指定代理类");
        }
        System.out.println("doSomething");
    }

    @Override
    public ForceSubject getProxy() {
        this.proxy = new ForceProxy(this);
        return this.proxy;
    }

    private boolean isProxy() {
        return this.proxy != null;
    }
}
public class ForceProxy implements ForceSubject{

    private final ForceSubject forceRealSubject;

    public ForceProxy(ForceSubject forceRealSubject) {
        this.forceRealSubject = forceRealSubject;
    }

    @Override
    public void doSomething() {
        this.doBefore();
        forceRealSubject.doSomething();
        this.doAfter();
    }

    @Override
    public ForceSubject getProxy() {
        return this;
    }

    private void doBefore() {
        System.out.println("doBefore");
    }

    private void doAfter() {
        System.out.println("doAfter");
    }
}

class ForceProxyTest {
    public static void main(String[] args) {
        ForceSubject forceSubject = new ForceRealSubject();
        try {
            forceSubject.doSomething();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 指定代理类
        ForceSubject proxy = forceSubject.getProxy();
        proxy.doSomething();
    }
}
