package com.kixs.algorithms.sort;

import com.kixs.constants.GlobalConstant;

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
        return GlobalConstant.RESULT_PATH + sortType() + GlobalConstant.PATH_SEPARATOR;
    }
}
