package com.kixs.leetcode;

import java.util.Arrays;

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
public class LeetCode_01386_CinemaSeatAllocation {

    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Arrays.sort(reservedSeats, (a, b) -> {
            if (a[0] < b[0]) {
                return -1;
            } else  if (a[0] > b[0]){
                return 1;
            } else {
                if (a[1] < b[1]) {
                    return -1;
                } else if (a[1] > b[1]){
                    return 1;
                }
            }
            return 0;
        });

        int[][] seats = new int[n][10];
        // 标记座位已选中
        for (int[] reservedSeat : reservedSeats) {
            seats[reservedSeat[0] - 1][reservedSeat[1] - 1] = 1;
        }
        int families = 0;
        // 每行可选的组合一共有三种：2,3,4,5; 4,5,6,7; 6,7,8,9
        // 每行可选的组合一共有三种：1,2,3,4; 3,4,5,6; 5,6,7,8
        for (int rowNum = 0; rowNum < n; rowNum++) {
            int[] row = seats[rowNum];
            if (row[3] == 0 && row[4] == 0 && row[1] == 0 && row[2] == 0) {
                row[3] = 1;
                row[4] = 1;
                row[1] = 1;
                row[2] = 1;
                families++;
            }
            if (row[3] == 0 && row[4] == 0 && row[5] == 0 && row[6] == 0) {
                row[3] = 1;
                row[4] = 1;
                row[5] = 1;
                row[6] = 1;
                families++;
            }
            if (row[5] == 0 && row[6] == 0 && row[7] == 0 && row[8] == 0) {
                row[5] = 1;
                row[6] = 1;
                row[7] = 1;
                row[8] = 1;
                families++;
            }
        }
        return families;
    }

    public static void main(String[] args) {
        int[][] reservedSeats = {{1, 2}, {1, 3}, {1, 8}, {2, 6}, {3, 1}, {3, 10}};
        int n = 3;
        System.out.println(new LeetCode_01386_CinemaSeatAllocation().maxNumberOfFamilies(n, reservedSeats));
    }
}
