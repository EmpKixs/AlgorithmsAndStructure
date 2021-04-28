package com.kixs.leetcode;

/**
 * url:https://leetcode-cn.com/problems/detect-pattern-of-length-m-repeated-k-or-more-times/
 * <p>
 * 给你一个正整数数组 arr，请你找出一个长度为 m 且在数组中至少重复 k 次的模式。
 * 模式 是由一个或多个值组成的子数组（连续的子序列），连续 重复多次但 不重叠 。 模式由其长度和重复次数定义。
 * 如果数组中存在至少重复 k 次且长度为 m 的模式，则返回 true ，否则返回  false 。
 * </p>
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/4/28 17:10
 */
public class LeetCode_01566_DetectPatternOfLengthMRepeatedKOrMoreTimes {

    public boolean containsPattern(int[] arr, int m, int k) {
        if (m * k > arr.length) {
            return false;
        }
        int minSize = m * 2;
        // 双重滑动窗格解决
        for (int mStart = 0; (arr.length - mStart) >= minSize; mStart++) {
            int mEnd = mStart + m - 1;
            int count = 1;
            boolean succession = true;
            for (int kStart = mEnd + 1; (arr.length - kStart) >= m; kStart++) {
                // int kEnd = kStart + m - 1;
                boolean flag = true;
                // 两个窗格依次比较
                for(int i = 0; i < m ; i++) {
                    if (arr[mStart + i] != arr[kStart + i]) {
                        flag = false;
                        succession = false;
                        break;
                    }
                }
                if (flag && ++count >= k) {
                    return true;
                }
                if (!succession) {
                    break;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2,2,2,2};
        int m = 1;
        int k = 4;
        System.out.println(new LeetCode_01566_DetectPatternOfLengthMRepeatedKOrMoreTimes().containsPattern(arr, m, k));
    }
}
