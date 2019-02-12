package com.kixs.util;

import com.kixs.algorithms.sort.AbstractSort;

/**
 * 排序比较工具
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
@SuppressWarnings("all")
public class SortCompareUtil extends AbstractSort {

    public static void show(Comparable[] data) {
        for (Comparable c : data) {
            System.out.print(c + " ");
        }
        System.out.println("");
    }

    /**
     * 判断数组是否有序
     */
    public static boolean isSorted(Comparable[] data) {
        if (data.length < 2) {
            return true;
        }
        for (int i = 0; i + 1 < data.length && !equal(data[i], data[i + 1]); i++) {
            if (less(data[i], data[i + 1])) {
                return isSortedAsc(data);
            } else {
                return isSortedDesc(data);
            }
        }
        return true;
    }

    /**
     * 判断文件中数据是否有序
     */
    public static boolean isSorted(String filePath) {
        Integer[] data = ResultReadFormFile.readFromFile(filePath);
        return isSorted(data);
    }

    /**
     * 判断数组是否升序
     */
    public static boolean isSortedAsc(Comparable[] data) {
        if (data.length < 2) {
            return true;
        }

        for (int i = 1; i < data.length; i++) {
            if (more(data[i - 1], data[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断数组是否降序
     */
    public static boolean isSortedDesc(Comparable[] data) {
        if (data.length < 2) {
            return true;
        }

        for (int i = 1; i < data.length; i++) {
            if (less(data[i - 1], data[i])) {
                return false;
            }
        }
        return true;
    }
}
