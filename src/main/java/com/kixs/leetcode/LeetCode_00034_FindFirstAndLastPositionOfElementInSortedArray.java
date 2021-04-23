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
        int left = -1;
        int right = -1;
        if (length > 0) {
            int position = binarySearch(nums, target);
            if (position > -1) {
                left = position;
                right = position;
                while (left - 1 > 0 && nums[left - 1] == target) {
                    left--;
                }
                while (right + 1 < length && nums[right + 1] == target) {
                    right++;
                }
            }
        }
        return new int[]{left, right};
    }

    private int binarySearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int position = -1;
        if (nums[low] <= target) {
            if (nums[low] == target) {
                position = low;
            }
            if (position == -1 && nums[high] >= target) {
                if (nums[high] == target) {
                    position = high;
                }
                while (position == -1 && low <= high) {
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
            }
        }

        return position;
    }

    public static void main(String[] args) {

        int[] nums = {0, 1, 2, 3, 4, 4, 4};
        int target = 5;
        int[] res = new LeetCode_00034_FindFirstAndLastPositionOfElementInSortedArray().searchRange(nums, target);
        System.out.println(res[0] + "," + res[1]);
    }

}
