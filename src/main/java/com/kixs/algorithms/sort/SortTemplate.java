package com.kixs.algorithms.sort;

import com.kixs.constants.GloableConstant;

/**
 * 排序模板接口
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
@SuppressWarnings("all")
public interface SortTemplate {

    String sortType();

    String methodName();

    void sort(Comparable[] data);

    default String resultPath() {
        return GloableConstant.RESULT_PATH + sortType() + GloableConstant.PATH_SEPARATOR;
    }
}
