package com.yc.juc;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduleThreadPoolDemo {
    private static class Task implements Runnable{

        private final Long id;
        private final AtomicInteger atomicInteger = new AtomicInteger(0);
        private final byte[] bytes = new byte[10 * 1024 * 1024];
        public Task(Long id){
            this.id = id;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务 " + id + " 执行任务啦 " + atomicInteger.incrementAndGet() + " " + LocalDateTime.now());
        }
    }

    public int nthUglyNumber(int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        int pointer2 = 1;
        int pointer3 = 1;
        int pointer5 = 1;
        for(int i=2;i<=n;i++){
            int curr2 = pointer2 * 2;
            int curr3 = pointer3 * 3;
            int curr5 = pointer5 * 5;
            dp[i] = Math.min(curr2, Math.min(curr3, curr5));
            // 要考虑去重，因为2*3 和3*2都可以到达6
            if(dp[i] == curr2){
                pointer2++;
            }
            if(dp[i] == curr3){
                pointer3++;
                // 因为只有这3中情况
            }
            if(dp[i] == curr5){
                pointer5++;
            }
        }
        return dp[n];
    }
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Task(1L), 2, 10, TimeUnit.SECONDS);
    }
}
