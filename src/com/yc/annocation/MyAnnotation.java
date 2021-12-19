package com.yc.annocation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface MyAnnotation {
    String value() default "hello";
}
