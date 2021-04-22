package com.kixs.leetcode;

/**
 * url:https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 * <p>
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * <p>
 *
 * @author wangbing
 * @version v1.0.0
 * @date 2020-04-21 18:16
 */
public class LeetCode_00034_FindFirstAndLastPositionOfElementInSortedArray {


    public int[] searchRange(int[] nums, int target) {
        int length = nums.length;
        if (length == 0) {
            return new int[]{-1, -1};
        }

        int low = 0;
        int high = length - 1;
        int position = -1;
        if (nums[low] == target) {
            position = low;
        }
        if (position == -1 && nums[high] == target) {
            position = high;
        }

        while (position == -1 && low < high) {
            int tmp = (high + low) / 2;
            if (nums[tmp] == target) {
                position = tmp;
            } else {
                if (nums[tmp] > target) {
                    high = tmp - 1;
                } else {
                    low = tmp + 1;
                }
            }
        }
        if (position == -1) {
            return new int[]{-1, -1};
        }

        int left = position - 1;
        int right = position + 1;
        while (left > 0 && nums[left] == target) {
            left--;
        }
        while (right < length && nums[right] == target) {
            right++;
        }

        return new int[]{left + 1, right - 1};
    }

    public static void main(String[] args) {

        int[] nums = {0,1,2,3,4,4,4};
        int target = 2;
        int[] res = new LeetCode_00034_FindFirstAndLastPositionOfElementInSortedArray().searchRange(nums, target);
        System.out.println(res[0] + "," + res[1]);
    }

}
