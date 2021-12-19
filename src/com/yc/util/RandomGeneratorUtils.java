package com.yc.util;

/**
 * 测试随机生成数
 */
public class RandomGeneratorUtils {
    public static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
