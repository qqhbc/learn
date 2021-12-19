package com.yc.juc;

import com.yc.MyException;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 缓存计算时间过长的数据
 *
 * @param <A>
 * @param <R>
 */
public interface Computable<A, R> {
    R compute(A a) throws InterruptedException, MyException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {

        TimeUnit.SECONDS.sleep(2);

        return new BigInteger(arg);
    }
}

/**
 * 可伸缩性差，每次只能有一个线程执行
 *
 * @param <A>
 * @param <R>
 */
class Memoizer1<A, R> implements Computable<A, R> {
    private final Map<A, R> cache = new HashMap<>();
    private final Computable<A, R> computable;

    public Memoizer1(Computable<A, R> computable) {
        this.computable = computable;
    }

    @Override
    public R compute(A a) throws InterruptedException, MyException {
        final R r;
        synchronized (this) {
            r = cache.get(a);
            if (Objects.isNull(r)) {
                R compute = computable.compute(a);
                cache.put(a, compute);
            }
        }
        return r;
    }
}

class Memoizer3<A, R> implements Computable<A, R> {

    private final Map<A, Future<R>> cache = new ConcurrentHashMap<>();
    private final Computable<A, R> computable;

    public Memoizer3(Computable<A, R> computable) {
        this.computable = computable;
    }

    @Override
    public R compute(A a) throws InterruptedException {
        Future<R> future = cache.get(a);
        if (Objects.isNull(future)) {
            FutureTask<R> futureTask = new FutureTask<>(() -> computable.compute(a));
            future = futureTask;
            // 在多线程访问下，可能存在覆盖情况，可能造成结果不一致的情况
            cache.put(a, futureTask);
            futureTask.run();
        }
        R r = null;
        try {
            r = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return r;
    }
}

class Memoizer<A, R> implements Computable<A, R> {

    private final Map<A, Future<R>> cache = new ConcurrentHashMap<>();
    private final Computable<A, R> computable;

    public Memoizer(Computable<A, R> computable) {
        this.computable = computable;
    }

    @Override

    public R compute(A a) throws InterruptedException, MyException {
        while (true) {
            Future<R> future = cache.get(a);
            if (Objects.isNull(future)) {
                FutureTask<R> futureTask = new FutureTask<>(() -> computable.compute(a));
                future = cache.putIfAbsent(a, futureTask);
                // putIfAbsent 属于原子操作(如果没有则添加)，再一次非空判断防止本此计算结果覆盖已被其他线程赋值 // todo
                if (Objects.isNull(future)) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                cache.remove(a, future);
            } catch (ExecutionException e) {
                throw new MyException("这是一个自定义异常");
            }
        }
    }

    public static void main(String[] args) throws MyException, InterruptedException {
        Computable<String, BigInteger> computable = new Memoizer<>(new ExpensiveFunction());
        new Thread(() -> {
            BigInteger hello_world = null;
            try {
                hello_world = computable.compute("1234568");
            } catch (InterruptedException | MyException e) {
                e.printStackTrace();
            }
            System.out.println(hello_world);
        }).start();
        new Thread(() -> {
            BigInteger hello_world = null;
            try {
                hello_world = computable.compute("123456877");
            } catch (InterruptedException | MyException e) {
                e.printStackTrace();
            }
            System.out.println(hello_world);
        }).start();
    }
}
