package com.kixs.algorithms.sort;

/**
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public abstract class AbstractSort {

    /**
     * 交换i,j位置的值
     */
    public static void exchange(Comparable[] data, int i, int j) {
        if (i >= data.length || j >= data.length) {
            return;
        }
        Comparable temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * 判断 a = b
     */
    public static boolean equal(Comparable a, Comparable b) {
        return a.equals(b);
    }

    /**
     * 判断 a < b
     */
    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    /**
     * 判断 a > b
     */
    public static boolean more(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }
}
