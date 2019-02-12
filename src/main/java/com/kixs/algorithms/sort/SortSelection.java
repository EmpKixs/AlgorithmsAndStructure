package com.kixs.algorithms.sort;

/**
 * 选择排序
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortSelection extends AbstractSort implements SortTemplate {

    @Override
    public String sortType() {
        return "Selection";
    }

    @Override
    public String methodName() {
        return "选择排序";
    }

    @Override
    public void sort(Comparable[] data) {
        int length = data.length;
        if (length < 2) {
            return;
        }
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (less(data[j], data[min])) {
                    min = j;
                }
            }
            exchange(data, i, min);
        }
    }
}
