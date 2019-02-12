package com.kixs.algorithms.sort.merge;

/**
 * 自底向上归并排序
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortMergeToTop extends SortMerge {

    @Override
    public String sortType() {
        return "MergeToTop";
    }

    @Override
    public String methodName() {
        return "归并排序";
    }

    @Override
    public void sort(Comparable[] data) {
        int length = data.length;
        if (length < 2) {
            return;
        }
        aux = new Comparable[data.length];
        for (int sz = 1; sz < length; sz = sz + sz) {
            for (int low = 0; low < length - sz; low = low + sz + sz) {
                merge(data, low, low + sz - 1, Math.min(low + sz + sz - 1, length - 1));
            }
        }
    }

}
