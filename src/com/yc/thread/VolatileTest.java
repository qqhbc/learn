package com.yc.thread;

public class VolatileTest {
    private int race = 3;

    public void increase() {
        race++;
    }

    public static void main(String[] args) {
        VolatileTest volatileTest = new VolatileTest();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    volatileTest.increase();
                }
            }).start();
        }
        System.out.println(volatileTest.race);
    }
}
