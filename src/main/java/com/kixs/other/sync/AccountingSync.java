package com.kixs.other.sync;

/**
 * @author wangbing
 * @version 1.0
 * @date 2019/4/3 15:39
 * Copyright: Copyright (c) 2019
 */
public class AccountingSync implements Runnable {
    //共享资源(临界资源)
    static int count = 0;

    /**
     * synchronized 修饰实例方法
     */
    public void increaseObj() {
        synchronized (AccountingSync.class) {
            count++;
        }
    }

    public static synchronized void increase() {
        count++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            increaseObj();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // test1();
        test2();

    }

    private static void test1() throws InterruptedException {
        AccountingSync instance = new AccountingSync();
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(new AccountingSync());
        Thread t2 = new Thread(new AccountingSync());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(count);
    }
}
