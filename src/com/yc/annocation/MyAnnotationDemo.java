package com.yc.annocation;

import org.junit.Test;

/**
 * 用于验证注解的一些问题
 */
@MyAnnotation("world")
public class MyAnnotationDemo {

    @Test
    public void getAnnotation() {
        MyAnnotation annotation = MyAnnotationDemo.class.getAnnotation(MyAnnotation.class);
        System.out.println(annotation.value());
    }
}
