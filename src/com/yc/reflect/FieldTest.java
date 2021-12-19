package com.yc.reflect;

import com.yc.entity.Person;
import org.junit.Test;

import java.lang.reflect.Field;

public class FieldTest {
    int x = 4100;

    @Test
    public void simpleField() {
        long y = 543216;
        Field[] declaredFields = Person.class.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
        }
        System.out.println((byte) y);
    }
}
