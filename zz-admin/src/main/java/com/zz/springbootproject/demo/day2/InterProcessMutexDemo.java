package com.zz.springbootproject.demo.day2;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CyclicBarrier;

/**
 * @description: Curator InterProcessMutex 可重入锁
 * @author: chenxue
 * @create: 2020-08-21 11:16
 */
public class InterProcessMutexDemo {
    public static void main(String[] args) {
        InterProcessMutexDemo demo = new InterProcessMutexDemo();
        int count = 20;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        demo.createClient();
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    demo.getLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // 重试次数
    private int retryCount = 5;

    // 重试间隔
    private int elapsedTimeMs = 5000;

    String addr= "192.168.133.129:2181,192.168.133.129:2182,192.168.133.129:2183";

    int timeOut = 60000;

    String server_path = "/curator";

    private CuratorFramework client = null;

    /**
     * @Description: 单例 创建连接
     * @param
     * @Author: chenxue
     * @Date: 2020-08-22 14:30
     */
    public void createClient(){
        if(null == client){
            synchronized (InterProcessMutexDemo.class){
                if(null == client){
                    RetryPolicy retryPolicy = new ExponentialBackoffRetry(retryCount,elapsedTimeMs);
                    client = CuratorFrameworkFactory
                            .builder()
                            .connectString(addr)
                            .sessionTimeoutMs(timeOut)
                            .retryPolicy(retryPolicy)
                            .build();
                    client.getConnectionStateListenable().addListener(new MyConnectionStateListener(server_path,"root"));
                    client.start();
                }
            }
        }
    }

    public void getLock() throws Exception {
        InterProcessMutex interProcessMutex = new InterProcessMutex(client,server_path);
        // 获取锁
        interProcessMutex.acquire();
        // 执行业务
        System.out.println("获取锁，开始执行业务逻辑。。。");
        Thread.sleep(2000);
        // 释放锁
        interProcessMutex.release();
    }
}
