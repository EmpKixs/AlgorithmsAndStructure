package com.kixs.util;

import com.kixs.constants.GloableConstant;

import java.io.IOException;
import java.util.Random;

/**
 * 生成随机数文件
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class RandomIntGenerator {

    private static final String DATA_FILE_NAME_PATTERN = "int-%s.txt";

    public static void main(String[] args) throws IOException {
        // 生成10、1k、1w、10w、100w个数
        generatorRandomInt(10);
        generatorRandomInt(100);
        generatorRandomInt(1000);
        generatorRandomInt(10000);
        generatorRandomInt(100000);
        generatorRandomInt(1000000);
    }

    public static void generatorRandomInt(int N) throws IOException {
        String fileName = GloableConstant.WHOLE_DATA_PATH + "int-" + N + ".txt";
        Integer[] data = new Integer[N];
        Random random = new Random(N);
        for (int i = 0; i < N; i++) {
            data[i] = Math.abs(random.nextInt(N));
        }
        ResultWriteToFile.writeToFile(data, fileName);
    }

    public static Integer[] getRandomIntFromFile(String filePath) {
        return ResultReadFormFile.readFromFile(filePath);
    }

    public static Integer[] getRandomInt(int limit) {
        return ResultReadFormFile.readFromFile(getDataFileName(limit));
    }

    public static String getDataFileName(int limit) {
        return GloableConstant.DATA_PATH + buildDataFileName(limit);
    }

    private static String buildDataFileName(int limit) {
        return String.format(DATA_FILE_NAME_PATTERN, limit);
    }
}
