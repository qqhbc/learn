package com.yc;

class Test{
    private String name;
    private Integer age;

    public Test(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
public class MyException extends Exception {
    public MyException(Throwable e) {
        super(e);
    }

    public MyException(String message) {
        super(message);
    }

    public static void main(String[] args) {
        Test hty = new Test("hty", 22);
        System.out.println(System.identityHashCode(hty));
        hty.setAge(33);
        System.out.println(System.identityHashCode(hty));
    }
}
