package com.zz.springbootproject.demo.day2;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: zookeeper 实现分布式锁
 * @author: chenxue
 * @create: 2020-08-20 16:15
 */
@Slf4j
public class LockSample {
    private String addr = "192.168.133.129:2181,192.168.133.129:2182,192.168.133.129:2183";
    private int timeOut = 60000;
    private ZooKeeper zooKeeper;
    private static final String LOCK_ROOT_PATH = "/Locks";
    private static final String LOCK_SUFFIX = "Lock_";
    private String lockPath;

    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            synchronized (this) {
                log.info("释放锁。。。");
                notifyAll();
            }
        }
    };


    public LockSample() throws IOException {
        zooKeeper = new ZooKeeper(addr, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Event.KeeperState.Disconnected == event.getState()) {
                    System.out.println("Connect is Break...");
                }
            }
        });
    }

    /**
     * @param
     * @Description: 获取锁
     * @Author: chenxue
     * @Date: 2020-08-20 16:27
     */
    public void acquireLock() throws KeeperException, InterruptedException {
        createLock();
        attemptLock();
    }


    /**
     * @param
     * @Description: 尝试获取锁
     * @Author: chenxue
     * @Date: 2020-08-20 16:27
     */
    public void attemptLock() throws KeeperException, InterruptedException {
        // 获取客户端在该节点下创建的节点，对所有客户端节点排序。在第一个的节点获取锁，其他的监听前一个节点。

        //1 、获取节点下所有子节点
        List<String> children = zooKeeper.getChildren(LOCK_ROOT_PATH, false);
        if (Objects.nonNull(children) && children.size() > 0) {
            Collections.sort(children);
            int idnex = children.indexOf(lockPath.substring(LOCK_ROOT_PATH.length() + 1));
            if (0 == idnex) {
                // 说明该节点排在第一位，获取锁
                log.info("{} get lock", Thread.currentThread().getName());
            } else {
                // 获取 前一个节点
                String s = children.get(idnex - 1);
                Stat exists = zooKeeper.exists(LOCK_ROOT_PATH + "/" + s, watcher);
                // 假如前一个节点不存在，则重新获取锁
                if (exists == null) {
                    attemptLock();
                } else {
                    // 假如前一个节点存在，则监听该节点. 线程等待watcher 释放锁信号
                    synchronized (watcher) {
                        watcher.wait();
                    }
                    attemptLock();
                }

            }
        }
    }

    /**
     * @param
     * @Description: 创建锁 每个客户端 需要竞争锁是会在该节点下创建对应的子节点
     * @Author: chenxue
     * @Date: 2020-08-20 16:27
     */
    public void createLock() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists(LOCK_ROOT_PATH, false);
        if (null == exists) {
            zooKeeper.create(LOCK_ROOT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        String lockPath = zooKeeper.create(LOCK_ROOT_PATH + "/" + LOCK_SUFFIX, Thread.currentThread().getName().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("{} create lock ： {}", Thread.currentThread().getName(), lockPath);
        this.lockPath = lockPath;
    }

    /**
     * @Description: 释放锁
     * @param
     * @Author: chenxue
     * @Date: 2020-08-21 10:48
     */
    public void releaseLock() throws KeeperException, InterruptedException {
        zooKeeper.delete(lockPath,-1);
        zooKeeper.close();
        log.info("{} close zookeeper",Thread.currentThread().getName());
    }
}
