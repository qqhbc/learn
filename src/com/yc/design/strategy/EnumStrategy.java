package com.yc.design.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumStrategy {
    STRATEGY_1("1") {
        @Override
        public void doSomething() {
            System.out.println("strategy_1 doSomething");
        }
    },
    STRATEGY_2("2") {
        @Override
        public void doSomething() {
            System.out.println("strategy_2 doSomething");
        }
    },
    ;
    private final String msg;

    public abstract void doSomething();
}

class EnumClient {
    public static void main(String[] args) {
        EnumStrategy.STRATEGY_1.doSomething();
        System.out.println("=================================");
        EnumStrategy.STRATEGY_2.doSomething();
    }
}
