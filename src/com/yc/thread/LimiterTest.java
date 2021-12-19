package com.yc.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流操作的几种简单方法
 * todo 暂时没有来得及系统的看
 */
interface Limiter{
    boolean acquire();
}
public class LimiterTest {
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));
    /**
     * 基数器方式
     */
   public static class Counter implements Limiter{

       private final int size = 10;
       private long timer = System.currentTimeMillis();
       // 间隔时间单位毫秒
       private final int interval = 1000;
       private final AtomicInteger atomicInteger = new AtomicInteger(0);
       @Override
       public boolean acquire() {
           long now = System.currentTimeMillis();
           if(now > interval + timer){
               timer = System.currentTimeMillis();
               atomicInteger.set(0);
           }else {
               return size > atomicInteger.getAndIncrement();
           }
           return false;
       }
   }

    /**
     * 漏斗桶方式
     * 以恒定速率出水，解决基数器时间片问题（服务器在临界值压力巨大）
     */
   public static class LeakyBucket implements Limiter{
       private final int size = 10;
       private long timer = System.currentTimeMillis();
       private AtomicInteger water = new AtomicInteger(0);
       // 出水速率
       private final int rate = 3;

       @Override
       public boolean acquire() {
           long l = System.currentTimeMillis();
           int out = (int) (l-timer) / 700 * rate;
           timer = l;
           water.set(Math.max(0, water.get() - out));
           return water.getAndIncrement() < size;
       }
   }

    /**
     * 令牌桶
     * 以恒定的速率先生成令牌，待桶满时不在生成，用户请求时可以快速获取，可以防止在大量请求访问时拒掉过多请求（漏斗桶缺点）
     */
   public static class TokenBucket implements Limiter{

       private final int size = 10;
       private long timer = System.currentTimeMillis();
       // 假设已经生成了10个令牌
       private final AtomicInteger token = new AtomicInteger(10);
       // 生成令牌桶的速率
       private final int rate = 3;

       @Override
       public boolean acquire() {
           long l = System.currentTimeMillis();
           token.addAndGet((int) (l-timer) / 50 * rate);
           timer = l;
           token.set(Math.min(size, token.get()));
           return token.getAndDecrement() > 0;
       }
   }

    @Test
    public void counterTest() throws InterruptedException {
        Counter counter = new Counter();
        for (int i = 0; i < 500; i++) {
            executor.execute(() -> {
                if(counter.acquire()){
                    System.out.println("获取一个许可");
                }else {
                    System.out.println("被限流啦！");
                }
            });
        }
        executor.shutdown();
    }

    @Test
    public void leakyBucketTest(){
        LeakyBucket leakyBucket = new LeakyBucket();
        for (int i = 0; i < 500; i++) {
            executor.execute(() -> {
                if(leakyBucket.acquire()){
                    System.out.println("获取一个许可");
                }else {
                    System.out.println("被限流啦！！");
                }
            });
        }
        executor.shutdown();
    }

    @Test
    public void tokenBucket() throws Exception{
        TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 500; i++) {
            executor.execute(() -> {
                if(tokenBucket.acquire()){
                    System.out.println("获取一个许可");
                }else {
                    System.out.println("被限流啦！！！");
                }
            });
        }
        executor.shutdown();
    }
}
