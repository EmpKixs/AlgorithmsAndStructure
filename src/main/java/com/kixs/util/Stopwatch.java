package com.kixs.util;

/**
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class Stopwatch {

    private final long start;

    public Stopwatch() {
        this.start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        return (System.currentTimeMillis() - start) / 1000.0;
    }

    public long elapsedMillis() {
        return System.currentTimeMillis() - start;
    }
}
