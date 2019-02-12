package com.kixs.constants;

/**
 * 全局常量
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class GloableConstant {

    public static final String PROJECT_DIRECTORY = System.getProperty("user.dir");

    public static final String PATH_SEPARATOR = System.getProperty("file.separator");

    public static final String RESULT_PATH = "result" + PATH_SEPARATOR;

    public static final String DATA_PATH = "data" + PATH_SEPARATOR;

    public static final String WHOLE_RESULT_PATH = PROJECT_DIRECTORY + PATH_SEPARATOR + RESULT_PATH;

    public static final String WHOLE_DATA_PATH = PROJECT_DIRECTORY + PATH_SEPARATOR + DATA_PATH;


}
