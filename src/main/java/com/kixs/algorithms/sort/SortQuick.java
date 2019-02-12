package com.kixs.algorithms.sort;

/**
 * 快速排序
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortQuick extends AbstractSort implements SortTemplate {

    @Override
    public String sortType() {
        return "Quick";
    }

    @Override
    public String methodName() {
        return "快速排序";
    }

    @Override
    public void sort(Comparable[] data) {
        if (data.length < 2) {
            return;
        }
        sort(data, 0, data.length - 1);
    }

    private void sort(Comparable[] data, int low, int high) {
        if (high <= low) {
            return;
        }
        int partition = partition(data, low, high);
        sort(data, low, partition - 1);
        sort(data, partition + 1, high);

    }

    private int partition(Comparable[] data, int low, int high) {
        int i = low, j = high + 1;

        Comparable v = data[i];
        while (true) {
            while (less(data[++i], v)) {
                if (i == high) {
                    break;
                }
            }
            while (less(v, data[--j])) {
                if (j == low) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exchange(data, i, j);
        }
        exchange(data, low, j);
        return j;
    }

}
