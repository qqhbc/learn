package com.yc.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 动态类型语言支持
 * 与反射有本质的区别，反射模拟的java代码层次的方法调用，而方法句柄模拟的字节码层次的方法调用
 */
public class MethodHandelTest {
    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
        getMethodHandle(obj).invokeExact("hello world");
    }

    private static MethodHandle getMethodHandle(Object obj) throws NoSuchMethodException, IllegalAccessException {
        // 方法宗量(方法类型，方法参数)
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(obj.getClass(), "println", methodType).bindTo(obj);
    }
}
