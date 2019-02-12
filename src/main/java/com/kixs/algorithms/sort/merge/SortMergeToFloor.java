package com.kixs.algorithms.sort.merge;

/**
 * 自顶向下归并排序
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortMergeToFloor extends SortMerge {

    @Override
    public String sortType() {
        return "MergeToFloor";
    }

    @Override
    public String methodName() {
        return "归并排序";
    }

    @Override
    public void sort(Comparable[] data) {
        if (data.length < 2) {
            return;
        }
        aux = new Comparable[data.length];
        sort(data, 0, data.length - 1);
    }

    private static void sort(Comparable[] data, int low, int high) {
        if (high <= low) {
            return;
        }
        int mid = low + (high - low) / 2;
        sort(data, low, mid);
        sort(data, mid + 1, high);
        merge(data, low, mid, high);
    }

}
