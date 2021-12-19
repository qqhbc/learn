package com.yc.jvm;

public class ClassLoaderTree {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaderTree.class.getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
    }
}
