package com.kixs.algorithms;

import com.kixs.algorithms.sort.*;
import com.kixs.algorithms.sort.merge.SortMergeToFloor;
import com.kixs.algorithms.sort.merge.SortMergeToTop;
import com.kixs.util.RandomIntGenerator;
import com.kixs.util.ResultWriteToFile;
import com.kixs.util.SortCompareUtil;
import com.kixs.util.Stopwatch;

/**
 * @author wangbing
 * @version 1.0, 2018/11/9
 */
public class SortTest {

    private static final String RESULT_FILE_NAME_PATTERN = "SortedResult-%s.txt";

    public static void main(String[] args) {
        showResultTitle();
        //int[] dataLimit = new int[]{10, 100, 1000, 10000, 100000};
        //int[] dataLimit = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        int[] dataLimit = new int[]{10};
        // 选择排序
        sortTest(dataLimit, new SortSelection());
        // 插入排序
        sortTest(dataLimit, new SortInsertion());
        // 希尔排序
        sortTest(dataLimit, new SortShell());
        // 自顶向下归并排序
        sortTest(dataLimit, new SortMergeToFloor());
        // 自底向上归并排序
        sortTest(dataLimit, new SortMergeToTop());
        // 快速排序
        sortTest(dataLimit, new SortQuick());
        // 三向切分快速排序
        sortTest(dataLimit, new SortQuick3Way());
    }

    public static void sortTest(int[] dataLimit, SortTemplate template) {
        for (int limit : dataLimit) {
            String dataFileName = RandomIntGenerator.getDataFileName(limit);
            Integer[] data = RandomIntGenerator.getRandomIntFromFile(dataFileName);
            Stopwatch timer = new Stopwatch();
            template.sort(data);
            double cost = timer.elapsedTime();
            String sortedResultFileName = getResultFileName(template, limit);
            ResultWriteToFile.writeToFile(data, sortedResultFileName);
            showResult(template.methodName(), dataFileName, sortedResultFileName, cost);
        }
    }

    private static String getResultFileName(SortTemplate template, int limit) {
        return template.resultPath() + buildResultName(limit);
    }

    private static String buildResultName(int limit) {
        return String.format(RESULT_FILE_NAME_PATTERN, limit);
    }

    public static void showResultTitle() {
        separatorLine();
        String title = String.format("|%-10s|%-20s|%-40s|%8s|%10s|", "排序方法", "测试数据", "结果集", "有序", "花费时间(s)");
        System.out.println(title);
        separatorLine();
    }

    public static void showResult(String methodName, String dataFileName, String resultFileName, double cost) {
        String row = String.format("|%-10s|%-23s|%-43s|%8s|%14.3f|", methodName, dataFileName, resultFileName, (SortCompareUtil.isSorted(resultFileName) ? "是" : "否"), cost);
        System.out.println(row);
        separatorLine();
    }

    public static void separatorLine() {
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }
}
