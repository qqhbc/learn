package com.yc.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

interface Test{
    int test(String name);
}
public class Observer {
    List<Test> list = new ArrayList<>();

    public void register(Test test){
        list.add(test);
    }

    public void print(){
        for (Test test : list) {
            int test1 = test.test("????");
            System.out.println(test1);
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        Observer observer = new Observer();
        observer.register(String::length);
        observer.print();
    }
}
