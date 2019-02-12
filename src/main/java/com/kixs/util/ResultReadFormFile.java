package com.kixs.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 从文件读取结果集
 *
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class ResultReadFormFile {

    public static Integer[] readFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new Integer[]{};
        }
        List<Integer> data = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String temp;
            while ((temp = reader.readLine()) != null) {
                data.add(Integer.valueOf(temp));
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

        return  data.toArray(new Integer[data.size()]);
    }
}
