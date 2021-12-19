package com.yc.jvm;

public class HotSwapClassLoader extends ClassLoader {
    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class<?> loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

    public static void main(String[] args) {
        HotSwapClassLoader classLoader = new HotSwapClassLoader();
    }
}
