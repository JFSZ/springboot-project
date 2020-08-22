package com.zz.springbootproject.demo.day3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @description: TODO
 * @author: chenxue
 * @create: 2020-08-22 10:46
 */
public class LockDemo {
    public static void main(String[] args) {
        int count = 20;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        IdGenerator idGenerator = new IdGenerator();
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("开始沉睡 。。。");
                    Thread.sleep(100);
                    System.out.println("结束沉睡 。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                idGenerator.createId();
            }, "Thread-" + i).start();
        }
    }
}
