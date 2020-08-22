package com.zz.springbootproject.demo.day2;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 测试
 * @author: chenxue
 * @create: 2020-08-21 11:10
 */
public class LockSampleTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    LockSample lockSample = new LockSample();
                    lockSample.createLock();
                    Thread.sleep((int)Math.random() * 2000);
                    lockSample.attemptLock();
                }
            });
        }
    }
}
