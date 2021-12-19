package com.yc.jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class BTraceTest {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BTraceTest traceTest = new BTraceTest();
        for (int i = 0; i < 10; i++) {
            reader.readLine();
            System.out.println(traceTest.add(random.nextInt(1000), random.nextInt(1000)));
        }
    }

}
