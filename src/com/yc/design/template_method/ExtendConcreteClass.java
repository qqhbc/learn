package com.yc.design.template_method;

import java.lang.reflect.Method;

abstract class ExtendAbstractClass {
    protected abstract void doSomething();
    protected abstract void doAnything();

    protected boolean isSomething() {
        return true;
    }

    public final void templateMethod() {
        if (isSomething()) {
            doSomething();
        }
        doAnything();
    }
}
public class ExtendConcreteClass extends ExtendAbstractClass {
    @Override
    protected void doSomething() {
        System.out.println("ExtendConcreteClass doSomething");
    }

    @Override
    protected void doAnything() {
        System.out.println("ExtendConcreteClass doAnything");
    }

    @Override
    protected boolean isSomething() {
        return false;
    }
}

class ExtendConcreteClass1 extends ExtendAbstractClass {
    private boolean flag = true;

    @Override
    protected void doSomething() {
        System.out.println("ExtendConcreteClass1 doSomething");
    }

    @Override
    protected void doAnything() {
        System.out.println("ExtendConcreteClass1 doAnything");
    }

    @Override
    protected boolean isSomething() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

class ExtendConcreteClassTest {
    public static void main(String[] args) throws Exception {
        ExtendAbstractClass extendAbstractClass = new ExtendConcreteClass();
        extendAbstractClass.templateMethod();
        System.out.println("=======================================");
        extendAbstractClass = new ExtendConcreteClass1();
        extendAbstractClass.templateMethod();
        System.out.println("=====================改变钩子函数=========================");
        Method method = ExtendConcreteClass1.class.getMethod("setFlag", boolean.class);
        method.invoke(extendAbstractClass, false);
        extendAbstractClass.templateMethod();
    }
}
