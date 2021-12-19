package com.yc.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CloneDemo {
    private static class Person implements Cloneable{
        private String name;
        private Integer age;
        private Address address;
        public Person(String name, Integer age){
            this.age = age;
            this.name = name;
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

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address=" + address +
                    '}';
        }

        protected Person clone() throws CloneNotSupportedException {
            Person clone = (Person) super.clone();
            clone.setAddress(address.clone());
            return clone;
        }
    }
    private static class Address implements Cloneable{
        private String detail;
        public Address(String detail){
            this.detail = detail;
        }

        public String toString() {
            return "Address{" +
                    "detail='" + detail + '\'' +
                    '}';
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        protected Address clone() throws CloneNotSupportedException {
            return (Address) super.clone();
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndIncrement();
        Person person = new Person("hty", 22);
        Address address = new Address("hunan");
        person.setAddress(address);
        System.out.println(person);
        System.out.println("============================");
        Person clone = person.clone();
        clone.setName("wpx");
        clone.getAddress().setDetail("henan");
        System.out.println("clone: " + clone);
        System.out.println("old: " + person);
    }
}
