package com.kixs.algorithms.sort;

/**
 * 希尔排序
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortShell extends AbstractSort implements SortTemplate {
    @Override
    public String sortType() {
        return "Shell";
    }

    @Override
    public String methodName() {
        return "希尔排序";
    }

    @Override
    public void sort(Comparable[] data) {
        int length = data.length;
        if (length < 2) {
            return;
        }

        int h = 1;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && less(data[j], data[j - h]); j -= h) {
                    exchange(data, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}
