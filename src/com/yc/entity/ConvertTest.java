package com.yc.entity;

import net.sf.cglib.beans.BeanCopier;
import org.junit.Test;

public class ConvertTest {

    @Test
    public void personConvertTest() {
        Person person = Person.builder()
                .address("hunan")
                .age(22)
                .name("hty")
                .build();
        PersonVO personVO = PersonVO.builder().build();
        BeanCopier beanCopier = BeanCopier.create(Person.class, PersonVO.class, false);
        beanCopier.copy(person, personVO, null);
        System.out.println(personVO);
    }
}
