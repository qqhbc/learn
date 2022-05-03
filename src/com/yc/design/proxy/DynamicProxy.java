package com.yc.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Advice {
    void exec();
}

class BeforeAdvice implements Advice {

    @Override
    public void exec() {
        System.out.println("before advice");
    }
}

class AfterAdvice implements Advice {

    @Override
    public void exec() {
        System.out.println("after advice");
    }
}

class MyInvocationHandler implements InvocationHandler {
    private final Subject object;

    public MyInvocationHandler(Subject object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 前置触发条件
        if (true) {
            Advice beforeAdvice = new BeforeAdvice();
            beforeAdvice.exec();
        }
        System.out.println("=================================");
        Object invoke = method.invoke(object, args);
        System.out.println("=================================");
        // 后置触发条件
        if (true) {
            Advice afterAdvice = new AfterAdvice();
            afterAdvice.exec();

        }

        return invoke;
    }
}


public class DynamicProxy {
    public static <T> T newProxyInstance(ClassLoader classLoader, Class<?>[] interfaces, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}

class DynamicProxyTest {
    public static void main(String[] args) throws Exception {
        Subject subject = new RealSubject();
        InvocationHandler invocationHandler = new MyInvocationHandler(subject);
        Subject proxy = DynamicProxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), invocationHandler);
        proxy.doSomething();
    }
}
