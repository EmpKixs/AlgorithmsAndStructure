package com.kixs.leetcode;

/**
 * url:https://leetcode-cn.com/problems/maximum-number-of-balloons/
 * <p>
 * 给你一个字符串 text，你需要使用 text 中的字母来拼凑尽可能多的单词 "balloon"（气球）。
 * <p>
 * 字符串 text 中的每个字母最多只能被使用一次。请你返回最多可以拼凑出多少个单词 "balloon"。
 *
 * @author wangbing
 * @version v1.0.0
 * @date 2019/9/26 10:25
 */
public class LeetCode_01189_MaxNumberOfBalloons {

    /**
     * 气球字母定义
     */
    private final static char B = 'b';
    private final static char A = 'a';
    private final static char L = 'l';
    private final static char O = 'o';
    private final static char N = 'n';

    /**
     * 组成单词最小长度限制
     */
    private final static int MINIMUM_NUMBER_LIMIT = 7;

    public int maxNumberOfBalloons(String text) {
        int length = text.length();
        if (length < MINIMUM_NUMBER_LIMIT) {
            return 0;
        }

        int b = 0;
        int a = 0;
        int l = 0;
        int o = 0;
        int n = 0;

        char[] letters = text.toCharArray();
        while (length > 0) {
            switch (letters[--length]) {
                case B:
                    b++;
                    break;
                case A:
                    a++;
                    break;
                case L:
                    l++;
                    break;
                case O:
                    o++;
                    break;
                case N:
                    n++;
                    break;
                default:
            }
        }

        int l1 = l / 2;
        int o1 = o / 2;

        int minNumber = b;
        if (minNumber > a) {
            minNumber = a;
        }
        if (minNumber > l1) {
            minNumber = l1;
        }
        if (minNumber > o1) {
            minNumber = o1;
        }
        if (minNumber > n) {
            minNumber = n;
        }

        return minNumber;
    }

    public static void main(String[] args) {
        System.out.println(new LeetCode_01189_MaxNumberOfBalloons().maxNumberOfBalloons("loonbalxballpoon"));
    }

}
