package com.kixs.util;

import com.kixs.constants.GloableConstant;

import java.io.*;

/**
 * 把结果集写入到文件中
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class ResultWriteToFile {

    static {
        initPath();
    }

    public static void initPath() {
        mkdir(GloableConstant.WHOLE_RESULT_PATH);
        mkdir(GloableConstant.WHOLE_DATA_PATH);
    }

    public static void writeToFile(Integer[] data, String fileName) {
        try {
            File file = new File(fileName);
            PrintStream printStream;
            try {
                printStream = new PrintStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                String wholeFilePath = GloableConstant.PROJECT_DIRECTORY + GloableConstant.PATH_SEPARATOR + fileName;
                mkdir(wholeFilePath.substring(0, wholeFilePath.lastIndexOf(GloableConstant.PATH_SEPARATOR)));
                printStream = new PrintStream(new FileOutputStream(file));
            }
            int N = data.length;
            int end = N - 1;
            for (int i = 0; i < N; i++) {
                if (i == end) {
                    printStream.print(data[i]);
                } else {
                    printStream.println(data[i]);
                }
            }
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean mkdir(String path) {
        File dir = new File(path);
        boolean existed = dir.exists();
        if (!existed) {
            existed = dir.mkdir();
            if (!existed) {
                boolean parentExisted = mkdir(path.substring(0, path.lastIndexOf(GloableConstant.PATH_SEPARATOR)));
                if (parentExisted) {
                    existed = dir.mkdir();
                }
            }
        }
        return existed;
    }
}
