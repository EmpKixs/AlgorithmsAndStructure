package com.kixs.other;

/**
 * @author wangbing
 * @version 1.0
 * @date 2019/3/21 11:12
 * Copyright: Copyright (c) 2019
 */
public class OtherTest {

    public static void main(String[] args) {
        System.out.println(15 & 31);
        System.out.println(15 & 32);
        System.out.println(15 & 33);
        System.out.println(15 & 34);
    }

    class A {
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

    }
}
