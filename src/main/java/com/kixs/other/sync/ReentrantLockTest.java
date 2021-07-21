package com.kixs.other.sync;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

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

    private final static ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2,
            30,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            new ThreadFactoryBuilder().setNameFormat("ReentrantLockTest-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
