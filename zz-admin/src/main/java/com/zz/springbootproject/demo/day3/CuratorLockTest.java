package com.zz.springbootproject.demo.day3;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * @description: curator 分布式锁测试
 * InterProcessMutex：分布式可重入排它锁
 * InterProcessSemaphoreMutex：分布式排它锁
 * InterProcessReadWriteLock：分布式读写锁
 * InterProcessMultiLock：将多个锁作为单个实体管理的容器
 * @author: chenxue
 * @create: 2020-08-25 11:46
 */
public class CuratorLockTest {
    public static void main(String[] args) {
        CuratorUtil curatorUtil = new CuratorUtil("");
        // 可重入锁
        InterProcessMutex lock = new InterProcessMutex(CuratorUtil.client,"/curator/app");
        try {
            //加锁
            lock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
