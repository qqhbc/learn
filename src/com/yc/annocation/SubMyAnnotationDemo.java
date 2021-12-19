package com.yc.annocation;

import java.lang.annotation.Annotation;

/**
 * 用于验证注解是否可以继承
 */
public class SubMyAnnotationDemo extends MyAnnotationDemo {
    public static void main(String[] args) {
        Annotation[] annotations = SubMyAnnotationDemo.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation.annotationType());
        }
    }
}
