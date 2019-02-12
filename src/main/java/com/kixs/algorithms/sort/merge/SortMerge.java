package com.kixs.algorithms.sort.merge;

import com.kixs.algorithms.sort.SortTemplate;
import com.kixs.algorithms.sort.AbstractSort;

/**
 * 归并排序抽象
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public abstract class SortMerge extends AbstractSort implements SortTemplate {

    protected static Comparable[] aux;

    protected static void merge(Comparable[] data, int low, int mid, int high) {
        int i = low, j = mid + 1;

        for (int k = low; k <= high; k++) {
            aux[k] = data[k];
        }
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                data[k] = aux[j++];
            } else if (j > high) {
                data[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                data[k] = aux[j++];
            } else {
                data[k] = aux[i++];
            }
        }
    }
}
