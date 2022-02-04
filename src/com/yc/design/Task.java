package com.yc.design;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Task implements Delayed {
    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    public static void main(String[] args) {
        String str = "和谐";
        String st1 = "社会";
        String str2 = str + st1;
        String str3 = "和谐社会";
        System.out.println(str2 == str3);
    }
}
