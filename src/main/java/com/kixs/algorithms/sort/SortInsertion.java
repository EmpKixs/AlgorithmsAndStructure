package com.kixs.algorithms.sort;

/**
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortInsertion extends AbstractSort implements SortTemplate {
    @Override
    public String sortType() {
        return "Insertion";
    }

    @Override
    public String methodName() {
        return "插入排序";
    }

    @Override
    public void sort(Comparable[] data) {
        int length = data.length;
        if (length < 2) {
            return;
        }

        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && less(data[j], data[j - 1]); j--) {
                exchange(data, j, j - 1);
            }
        }
    }
}
