package com.kixs.other.test2;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 列表测试
 *
 * @author wangbing
 * @since 2021/10/18 11:17
 */
public class ListTest {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        List<String> link = Lists.newLinkedList();
        int limit = 10000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            list.add("list--------------\t" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("list-A:\t" + (end - start) + "ms");
        start = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            link.add("link--------------\t" + i);
        }
        end = System.currentTimeMillis();
        System.out.println("link-A:\t" + (end - start) + "ms");


        start = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            list.add(5000, "list--------------\t" + i);
        }
        end = System.currentTimeMillis();
        System.out.println("list-B:\t" + (end - start) + "ms");
        start = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            link.add(5000, "link--------------\t" + i);
        }
        end = System.currentTimeMillis();
        System.out.println("link-B:\t" + (end - start) + "ms");



        start = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            list.add(0, "list--------------\t" + i);
        }
        end = System.currentTimeMillis();
        System.out.println("list-C:\t" + (end - start) + "ms");
        start = System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            link.add(0, "link--------------\t" + i);
        }
        end = System.currentTimeMillis();
        System.out.println("link-C:\t" + (end - start) + "ms");
    }
}
