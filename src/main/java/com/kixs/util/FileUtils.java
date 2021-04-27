package com.kixs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件工具类
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/4/27 10:03
 */
public class FileUtils {

    public static String read(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File Not Found：" + filePath);
        }
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
