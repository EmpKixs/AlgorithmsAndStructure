package com.kixs.other.hashmap;

import java.util.HashMap;

/**
 * @author wangbing
 * @version 1.0
 * @date 2019/3/28 14:53
 * Copyright: Copyright (c) 2019
 */
public class HashMapTest {

    public static void main(String[] args) {

        HashMap<DataKey, Integer> map = new HashMap<>(128);
        for (int i = 0; i < 10; i++) {
            if (i > 7) {
                System.out.println(i);
            }
            map.put(new DataKey("0"), i);
        }

        System.out.println(map.toString());
    }
}
