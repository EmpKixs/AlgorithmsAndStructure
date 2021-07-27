package com.kixs.other.sync;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLockTest
 *
 * @author wangbing
 * @version v1.0.0
 * @since 2021/7/21 17:45
 */
public class ReentrantLockTest {

    private static ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            new ThreadFactoryBuilder().setNameFormat("ReentrantLockTest-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        int limit = 10;
        for (int i = 0; i < limit; i++) {
            // 无锁操作自增结果 <--> 加锁lock操作自增结果 <--> 加锁tryLock操作自增结果
            System.out.println(incrTest(300).getSeed() + " <---> " + incrLockTest(300).getSeed() + " <---> " + incrTryLockTest(300).getSeed());
        }
        POOL.shutdown();
        POOL = null;
    }

    /**
     * 无锁自增操作
     *
     * @param limit 循环自增次数
     * @return 自增结果
     */
    public static Seed incrTest(int limit) {
        Seed seed = new Seed(1);
        List<CompletableFuture<Void>> futures = Lists.newArrayList();
        for (int i = 0; i < limit; i++) {
            futures.add(CompletableFuture.runAsync(seed::incrTen, POOL));
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        allOf.join();
        return seed;
    }

    /**
     * 加锁自增操作
     *
     * @param limit 循环自增次数
     * @return 自增结果
     */
    public static Seed incrLockTest(int limit) {
        Seed seed = new Seed(1);
        List<CompletableFuture<Void>> futures = Lists.newArrayList();
        for (int i = 0; i < limit; i++) {
            futures.add(CompletableFuture.runAsync(seed::incrLockTen, POOL));
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        allOf.join();
        return seed;
    }

    /**
     * 加锁自增操作
     *
     * @param limit 循环自增次数
     * @return 自增结果
     */
    public static Seed incrTryLockTest(int limit) {
        Seed seed = new Seed(1);
        List<CompletableFuture<Void>> futures = Lists.newArrayList();
        for (int i = 0; i < limit; i++) {
            futures.add(CompletableFuture.runAsync(seed::incrTryLockTen, POOL));
        }
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        allOf.join();
        return seed;
    }

    static class Seed {
        static int TEN = 10;
        ReentrantLock lock = new ReentrantLock();
        int seed = 0;

        Seed(int seed) {
            this.seed = seed;
        }

        void incrTen() {
            for (int i = 0; i < TEN; i++) {
                seed++;
            }
        }

        void incrLockTen() {
            lock.lock();
            try {
                incrTen();
            } finally {
                lock.unlock();
            }
        }

        void incrTryLockTen() {
            boolean locked = false;
            try {
                locked = lock.tryLock(10, TimeUnit.SECONDS);
                if (locked) {
                    incrTen();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (locked) {
                    lock.unlock();
                }
            }
        }

        int getSeed() {
            return seed;
        }
    }
}
