package com.yc.juc;

import com.yc.entity.Person;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UnsafeTest {
    Integer i = 10;
    private static final UnsafeTest unsafeTest = new UnsafeTest();
    private final Person person = new Person();

    @Test
    public void atomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger();
        int i = atomicInteger.get();
        System.out.println(i); // 0
        boolean b = atomicInteger.compareAndSet(1, 2);
        System.out.println(b); // false
        System.out.println(atomicInteger.get()); // 0
        System.out.println(atomicInteger.compareAndSet(0, 100));  // true
        System.out.println(atomicInteger.get()); // 100
    }

    @Test
    public void unsafeTest() throws Exception {
        Field f = Unsafe.class.getDeclaredFields()[0];
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        Field i = UnsafeTest.class.getDeclaredField("i");
        long l = unsafe.objectFieldOffset(i);
        System.out.println(i.get(unsafeTest));
        System.out.println(l);
        boolean b = unsafe.compareAndSwapInt(unsafeTest, l, 10, 100); // false 成员变量i是对象
        System.out.println(b);
        System.out.println(unsafeTest.i);
    }

    @Test
    public void atomicReference() {
        AtomicReference<Person> atomicReference = new AtomicReference<>();
        person.setName("hty");
        person.setBirthday(LocalDate.of(2020, 11, 30));
        atomicReference.getAndSet(person);
        System.out.println(atomicReference.get());
        Person per = new Person();
        //person.setName("aabbb");
        boolean b = atomicReference.compareAndSet(per, person);
        System.out.println(b);
        System.out.println(atomicReference.get());
        System.out.println(person);
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        System.out.println(list.toString());
    }

}
