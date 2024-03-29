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
public class LeetCode_01386_CinemaSeatAllocation {

    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Arrays.sort(reservedSeats, Comparator.comparingInt(x -> x[0]));
        int families = 0;
        int reservedRowNum = 0;
        for (int i = 0; i < reservedSeats.length; ) {
            // 每行可选的组合一共有三种：2,3,4,5; 4,5,6,7; 6,7,8,9
            // 每行可选的组合一共有三种：1,2,3,4; 3,4,5,6; 5,6,7,8
            reservedRowNum++;
            int rowNum = reservedSeats[i][0];
            boolean flag23 = false, flag45 = false, flag67 = false, flag89 = false;
            do {
                switch (reservedSeats[i][1]) {
                    case 2:
                    case 3:
                        flag23 = true;
                        break;
                    case 4:
                    case 5:
                        flag45 = true;
                        break;
                    case 6:
                    case 7:
                        flag67 = true;
                        break;
                    case 8:
                    case 9:
                        flag89 = true;
                    default:
                }
            } while (++i < reservedSeats.length && rowNum == reservedSeats[i][0]);
            if (!flag23 && !flag45) {
                families++;
                flag45 = true;
            }
            if (!flag45 && !flag67) {
                families++;
                flag67 = true;
            }
            if (!flag67 && !flag89) {
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
        System.out.println(new LeetCode_01386_CinemaSeatAllocation().maxNumberOfFamilies(n, reservedSeats));
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
        System.out.println(new LeetCode_01386_CinemaSeatAllocation().maxNumberOfFamilies(n, reservedSeats));
        System.out.println(stopwatch.elapsedMillis());
    }
}
