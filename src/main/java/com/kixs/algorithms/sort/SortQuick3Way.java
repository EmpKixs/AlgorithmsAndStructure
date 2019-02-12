package com.kixs.algorithms.sort;

/**
 * 三向切分快速排序
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortQuick3Way extends AbstractSort implements SortTemplate {

    @Override
    public String sortType() {
        return "Quick3Way";
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

        int lt = low, i = low + 1, gt = high;
        Comparable v = data[low];
        while (i <= gt) {
            int result = data[i].compareTo(v);
            if (result < 0) {
                exchange(data, lt++, i++);
            } else if (result > 0) {
                exchange(data, i, gt--);
            } else {
                i++;
            }
        }

        sort(data, low, lt - 1);
        sort(data, gt + 1, high);

    }

}
