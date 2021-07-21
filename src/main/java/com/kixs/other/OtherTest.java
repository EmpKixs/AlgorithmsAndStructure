package com.kixs.other;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangbing
 * @version 1.0
 * @date 2019/3/21 11:12
 * Copyright: Copyright (c) 2019
 */
public class OtherTest {

    public static void main(String[] args) {
        /*intBoxingTest();
        intBoundTest();
        floatCompareTest();
        Child child = new Child();
        System.out.println();
        stringCompareTest();
        hashMapTest();
        setTest();
        concurrentHashMapTest();*/
        bitTest();
    }

    public static void bitTest() {
        // 1-2-8-2-8-7-4
        // 0-11-00000001-00-00000000-0000000-0000
        int b = 0b01100001101000000010100000000000;
        System.out.println(b);
    }

    public static void intBoxingTest() {
        System.out.println("装箱拆箱机制测试：");
        Integer a = 128;
        int b = 128;
        Integer c = b;
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a == c);
        System.out.println(a.equals(c));
    }

    public static void intBoundTest() {
        System.out.println("边界值测试：");
        int max = Integer.MAX_VALUE;
        System.out.println(max);
        System.out.println(max + 1);
        System.out.println(Integer.MIN_VALUE);
    }

    public static void floatCompareTest() {
        System.out.println("浮点数比较测试：");
        float a = 1.1F;
        float b = 11 / 10;
        Float a1 = 1.1F;
        // Float b1 = 11/10;
        System.out.println(a == b && a == a1);
        // System.out.println(a1 == b1 && b == b1);
    }

    public static void stringCompareTest() {
        System.out.println("字符串比较测试：");
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String(str2);
        System.out.println(str1 == str2 && str1 == str3);

        StringBuilder builder1 = new StringBuilder();
        builder1.append("ab").append("c");
        StringBuilder builder2 = new StringBuilder();
        builder2.append("ab").append("c");
        System.out.println(builder1 == builder2);
        System.out.println(builder1.equals(builder2));
        System.out.println(builder1.toString() == builder2.toString());
    }

    public static void hashMapTest() {
        System.out.println("HashMap容量测试：");
        HashMap map = new HashMap(10);
        map.put("A", 1);
    }

    public static void setTest() {
        HashSet<Integer> a = new HashSet<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);

        HashSet<Integer> b = new HashSet<>();
        b.add(3);
        b.add(4);
        b.add(5);
        b.add(6);
        b.add(7);
        System.out.println(Sets.union(a, b));
        System.out.println(Sets.difference(a, b));
        System.out.println(Sets.difference(b, a));
        System.out.println(Sets.intersection(a, b));
        System.out.println(Sets.symmetricDifference(a, b));
    }

    public static void concurrentHashMapTest() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        map.put("A", null);
        map.put(null, 1);

    }

}
