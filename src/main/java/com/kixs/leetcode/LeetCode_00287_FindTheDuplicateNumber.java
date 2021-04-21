package com.kixs.leetcode;

/**
 * url:https://leetcode-cn.com/problems/find-the-duplicate-number/
 * <p>
 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
 * <p>
 *
 * @author wangbing
 * @version v1.0.0
 * @date 2020-04-21 18:16
 */
public class LeetCode_00287_FindTheDuplicateNumber {


    public int findDuplicate(int[] nums) {
        return nums[0];
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 2, 2};
        System.out.println(new LeetCode_00287_FindTheDuplicateNumber().findDuplicate(nums));
    }

}
