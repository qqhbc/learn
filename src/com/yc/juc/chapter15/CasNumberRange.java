package com.yc.juc.chapter15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 通过CAS来维持包含多个变量的不变性条件
 */
public class CasNumberRange {
    private static class IntPair {
        // 不变性条件 upper > lower
        final int lower;
        final int upper;

        IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<IntPair> atomicReference = new AtomicReference<>(new IntPair(0, 0));

    public int getLower() {
        return atomicReference.get().lower;
    }

    public int getUpper() {
        return atomicReference.get().upper;
    }

    public void setLower(int i) {
        while (true) {
            IntPair oldPair = atomicReference.get();
            if (i > oldPair.upper) {
                throw new IllegalArgumentException("Can't set lower to " + i + "> upper");
            }
            IntPair newPair = new IntPair(i, oldPair.upper);
            if (atomicReference.compareAndSet(oldPair, newPair)) {
                return;
            }
        }
    }

    public void setUpper(int i) {
        while (true) {
            IntPair oldPair = atomicReference.get();
            if (i < oldPair.lower) {
                throw new IllegalArgumentException("Can't set upper to " + i + "< lower");
            }
            IntPair newPair = new IntPair(oldPair.lower, i);
            if (atomicReference.compareAndSet(oldPair, newPair)) {
                return;
            }
        }
    }
}
