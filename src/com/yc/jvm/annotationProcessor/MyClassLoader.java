package com.yc.jvm.annotationProcessor;

import java.io.*;
import java.lang.reflect.Method;

public class MyClassLoader {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                System.out.println("xxxxxxxxxx");
                String localName = "F:" + File.separator + "Hello.class";

                try (InputStream input = new FileInputStream(localName);
                     ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    byte[] bytes = new byte[1024];
                    int temp;
                    while ((temp = input.read(bytes)) != -1) {
                        out.write(bytes, 0, temp);
                    }
                    byte[] bytes1 = out.toByteArray();
                    return super.defineClass(name, bytes1, 0, bytes1.length);
                } catch (IOException e) {
                    throw new IllegalArgumentException("xxx", e);
                }
            }
        };
        Class<?> aClass = classLoader.loadClass("com.yc.jvm.Hello");
        Object o = aClass.newInstance();
        Method say = aClass.getDeclaredMethod("say");
        say.invoke(o);
    }
}
