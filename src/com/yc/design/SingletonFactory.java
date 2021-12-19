package com.yc.design;

class Resource {
    public void test(){
        System.out.println("hello world");
    }
}

/**
 * 延长初始化占位类模式(静态内部类)</br>
 * <Strong>JVM将推迟ResourceHolder的初始化操作，直到开始使用这个类(Resource)是才初始化</Strong>
 *
 * @see Singleton 比DCL具有更好的效率
 */
public class SingletonFactory {
    private SingletonFactory(){}
    private static class ResourceHolder {
        public static Resource singleton = new Resource();
    }

    public static Resource getResource() {
        return ResourceHolder.singleton;
    }

    public static void main(String[] args) {
        SingletonFactory.getResource().test();
    }
}
