package com.yc.jvm.annotationProcessor;

import java.util.HashMap;

public class Hello extends HashMap {
    static {
        System.out.println("hello");
    }

    public void say() {
        System.out.println("world");
    }

    public static void main(String[] args) {

    }
}
