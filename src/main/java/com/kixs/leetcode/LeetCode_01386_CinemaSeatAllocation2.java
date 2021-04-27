package com.kixs.leetcode;

import com.kixs.constants.GlobalConstant;
import com.kixs.util.FileUtils;
import com.kixs.util.Stopwatch;

import java.util.Arrays;
import java.util.Comparator;

/**
 * url:https://leetcode-cn.com/problems/cinema-seat-allocation/
 * <p>
 * 电影院的观影厅中有 n 行座位，行编号从 1 到 n ，且每一行内总共有 10 个座位，列编号从 1 到 10 。
 * 给你数组 reservedSeats ，包含所有已经被预约了的座位。比如说，researvedSeats[i]=[3,8] ，它表示第 3 行第 8 个座位被预约了。
 * 请你返回 最多能安排多少个 4 人家庭 。4 人家庭要占据 同一行内连续 的 4 个座位。隔着过道的座位（比方说 [3,3] 和 [3,4]）不是连续的座位，但是如果你可以将 4 人家庭拆成过道两边各坐 2 人，这样子是允许的。
 * </p>
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/4/26 18:03
 */
public class LeetCode_01386_CinemaSeatAllocation2 {

    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Arrays.sort(reservedSeats, Comparator.comparingInt(x -> x[0]));
        int families = 0;
        int reservedRowNum = 0;
        int left = 0b11110000;
        int middle = 0b00111100;
        int right = 0b00001111;
        for (int i = 0; i < reservedSeats.length; ) {
            // 每行可选的组合一共有三种：2,3,4,5; 4,5,6,7; 6,7,8,9
            // 每行可选的组合一共有三种：1,2,3,4; 3,4,5,6; 5,6,7,8
            reservedRowNum++;
            int rowNum = reservedSeats[i][0];
            int row = 0b00000000;
            do {
                switch (reservedSeats[i][1]) {
                    case 2:
                        row += 0b10000000;
                        break;
                    case 3:
                        row += 0b01000000;
                        break;
                    case 4:
                        row += 0b00100000;
                        break;
                    case 5:
                        row += 0b00010000;
                        break;
                    case 6:
                        row += 0b00001000;
                        break;
                    case 7:
                        row += 0b00000100;
                        break;
                    case 8:
                        row += 0b00000010;
                        break;
                    case 9:
                        row += 0b00000001;
                        break;
                    default:
                }
            } while (++i < reservedSeats.length && rowNum == reservedSeats[i][0]);
            boolean flagL = (left ^ row & left) == left;
            if (flagL) {
                families++;
            }
            boolean flagM = !flagL && (middle ^ row & middle) == middle;
            if (flagM) {
                families++;
            }
            if (!flagM && (right ^ row & right) == right) {
                families++;
            }
        }
        families += 2 * (n - reservedRowNum);
        return families;
    }

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        int[][] reservedSeats = {{1, 2}, {1, 3}, {1, 8}, {2, 6}, {3, 1}, {3, 10}};
        int n = 3;
        Stopwatch stopwatch = new Stopwatch();
        System.out.println(new LeetCode_01386_CinemaSeatAllocation2().maxNumberOfFamilies(n, reservedSeats));
        System.out.println(stopwatch.elapsedMillis());
    }

    private static void test2() {
        String filePath = GlobalConstant.PROJECT_DIRECTORY + GlobalConstant.PATH_SEPARATOR + GlobalConstant.DATA_PATH + "leetcode" + GlobalConstant.PATH_SEPARATOR + "LeetCode_01386_CinemaSeatAllocation.txt";
        String data = FileUtils.read(filePath);
        data = data.substring(1, data.length() - 1);
        String[] split = data.split("},");
        int[][] reservedSeats = new int[split.length][2];
        int index = 0;
        for (String s : split) {
            if (s.contains("}")) {
                s = s.substring(1, s.length() - 1);
            } else {
                s = s.substring(1);
            }
            String[] nums = s.split(",");
            int[] pos = new int[]{Integer.valueOf(nums[0]), Integer.valueOf(nums[1])};
            reservedSeats[index++] = pos;
        }
        int n = 1000000000;
        Stopwatch stopwatch = new Stopwatch();
        System.out.println(new LeetCode_01386_CinemaSeatAllocation2().maxNumberOfFamilies(n, reservedSeats));
        System.out.println(stopwatch.elapsedMillis());
    }
}
