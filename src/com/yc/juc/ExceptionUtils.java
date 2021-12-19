package com.yc.juc;

public class ExceptionUtils {
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Uncheck Exception", t);
        }
    }
}
