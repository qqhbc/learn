package com.yc.design;

import sun.misc.ProxyGenerator;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 最简单的动态代理实现
 */
interface RealRun {
    void run(String s);

    void hello();

    default void test() {
        hello();
    }
}

class RealRunImpl implements RealRun {

    @Override
    public void run(String s) {
        System.out.println("天晴了，雨停了，俺要出去跑步了！" + s);
    }

    public void hello() {
        System.out.println("hello world");
    }
}

public class DynamicProxyHandle implements InvocationHandler {

    private Object target;

    public DynamicProxyHandle(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("准备出发！！");
        System.out.println(method.getName());
        Object invoke = method.invoke(target, args);
        System.out.println("已经跑步啦！！！");
        return invoke;
    }

    private static void byteToFile(String filePath, byte[] bytes) throws Exception {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath))) {
            out.write(bytes);
        }
    }

    public static void main(String[] args) throws Exception {
        RealRun realRun = new RealRunImpl();
        realRun.test();
        DynamicProxyHandle dynamicProxy = new DynamicProxyHandle(realRun);
        RealRun o = (RealRun) Proxy.newProxyInstance(realRun.getClass().getClassLoader(), new Class[]{RealRun.class}, dynamicProxy);
        System.out.println(o.getClass().getName());
        o.run("hello world");

        byte[] bytes = ProxyGenerator.generateProxyClass("DynamicProxy", new Class[]{RealRun.class});
        byteToFile("D:\\workspace\\practice\\DynamicProxy.class", bytes);
    }
}
