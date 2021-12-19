package com.yc.exception;

import java.util.concurrent.*;

class NoClassDefFoundErrorDemo{
    public NoClassDefFoundErrorDemo(){
        new InnerClass();
    }
    static class InnerClass{
        static int a;
        private static int i;

        static {
            a = 10 / i;
        }

        public static void setI(int i) {
            InnerClass.i = i;
        }
    }


}
public class TestBase {
    public void test() throws BaseException {
        System.out.println("Test Base");
        throw new BaseException("throw Base Exception");
    }

    public static class TestMiddle extends TestBase{
        @Override
        public void test() throws MiddleException {
            System.out.println("Test Middle");
            throw new MiddleException("throw Middle exception");
        }
    }

    public static class TestBottom extends TestMiddle{
        @Override
        public void test() throws BottomException {
            System.out.println("Test bottom");
            throw new BottomException("throw Bottom Exception");
        }
    }

    public static void main(String[] args) throws Exception {

        ThreadPoolExecutor service = new ThreadPoolExecutor(1, 1,
                20L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        Future<Object> future = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(10000);
            System.out.println("正在执行");
            return "success";
        });
        if (!future.isDone()) {
            Object o = future.get();
            System.out.println(o);
        }
        System.out.println("hello world");
        if (!service.allowsCoreThreadTimeOut()) {
            service.allowCoreThreadTimeOut(true);
        }
    }
}