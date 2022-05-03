package com.yc.design.adapter;

/**
 * 已存在的目标对象
 */
interface Target {
    void request();
}

/**
 * 源角色也存在
 */
class Adaptee {
    public void specificRequest() {
        System.out.println("specificRequest");
    }
}

/**
 * 一种补偿模式</br>
 * 对已运行项目的对象进行适配
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        super.specificRequest();
    }
}

class Client {
    public static void main(String[] args) {
        Target target = new Target() {
            @Override
            public void request() {
                System.out.println("Target request");
            }
        };
        target.request();
        System.out.println("===============================");
        Target target1 = new Adapter();
        target1.request();
    }
}
