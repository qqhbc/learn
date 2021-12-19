package com.yc.lamdba;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;
    private int age = 10;

    // 利用 value 属性缓存 supplier 计算后的值
    private T value;

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    /**
     * 函子（单位元定律 + 复合定律）
     * <a href="https://mp.weixin.qq.com/s/e-9hrjWK513VJqqyeGLxrQ">文章地址</a>
     */
    public <S> Lazy<S> map(Function<? super T,? extends S> function) {
        return Lazy.of(() -> function.apply(get()));
    }

    public void print() {
        System.out.println(age);
        int age = 100;
        System.out.println(age);
    }


    @Override
    public T get() {
        if (value == null) {
            T newValue = supplier.get();

            if(newValue == null) {
                throw new IllegalStateException("Lazy value can not be null!");
            }

            value = newValue;
        }

        return value;
    }

    private static AtomicInteger i = new AtomicInteger(10);

    public static void change(int x) {
        x = 100;
    }

    public static void main(String[] args) {
        change(i.get());
        System.out.println(i);


    }
}
