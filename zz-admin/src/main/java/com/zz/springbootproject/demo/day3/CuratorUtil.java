package com.zz.springbootproject.demo.day3;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.time.LocalDateTime;

/**
 * @description: curator 工具类 练习
 * curator 解决问题：
 * 解决Watch注册一次就会失效的问题
 * 支持直接创建多级结点
 * 提供的 API 更加简单易用
 * 提供更多解决方案并且实现简单，例如：分布式锁
 * 提供常用的ZooKeeper工具类
 * 编程风格更舒服
 * @author: chenxue
 * @create: 2020-08-22 14:42
 */
@Slf4j
public class CuratorUtil {
    // 一个zookeeper集群只需要一个 client。劲量保证单例
    private static CuratorFramework client;
    // zk 服务端集群地址
    private String connectString = "192.168.133.129:2181,192.168.133.129:2182,192.168.133.129:2183";

    // session 超时时间
    private int timeOut = 60000;

    // zkclient 重试间隔时间
    private int baseSleepTimeMs = 5000;

    //zkclient 重试次数
    private int retryCount = 5;

    public CuratorUtil() {
        init();
    }

    /**
     * @param
     * @Description: 创建客户端
     * @Author: chenxue
     * @Date: 2020-08-22 14:57
     */
    public void init() {
        if (null == client) {
            synchronized (CuratorUtil.this) {
                if (null == client) {
                    client = CuratorFrameworkFactory
                            .builder()
                            .connectString(connectString)
                            .sessionTimeoutMs(timeOut)
                            .retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, retryCount))
                            .namespace("curator")
                            .build();
                    client.start();
                    log.info("client is created at {}", LocalDateTime.now());
                }
            }
        }
    }

    /**
     * @param
     * @Description: 关闭连接
     * @Author: chenxue
     * @Date: 2020-08-22 15:15
     */
    public void closeConnection() {
        if (null != client) {
            client.close();
        }
    }

    /**
     * @param path
     * @param value
     * @Description: 创建节点
     * @Author: chenxue
     * @Date: 2020-08-22 15:15
     */
    public void createNode(String path, String value) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        String node = client
                .create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL) // 临时顺序节点
                .forPath(path, value.getBytes());

        log.info("create node : {}", node);
    }

    /**
     * @param path
     * @Description: 删除节点信息
     * @Author: chenxue
     * @Date: 2020-08-22 16:01
     */
    public void deleteNode(String path) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        client
                .delete()
                .deletingChildrenIfNeeded()
                .forPath(path);
        log.info("{} is deleted ", path);
    }


    /**
     * @param path
     * @Description: 获取节点存储的值
     * @Author: chenxue
     * @Date: 2020-08-22 16:11
     */
    public String getNodeData(String path) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        log.info("{} data is : {}", path, new String(bytes));
        log.info("current stat version is {}, createTime is {}", stat.getVersion(), stat.getCtime());
        return new String(bytes);
    }


    /**
     * @Description: 设置节点 数据
     * @param path
     * @param value
     * @Author: chenxue
     * @Date: 2020-08-22 16:17
     */
    public void setNodeData(String path,String value) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        Stat stat = client.checkExists().forPath(path);
        if(null == stat){
            log.info(String.format("{} Znode is not exists",path));
            throw new RuntimeException(String.format("{} Znode is not exists",path));
        }
        String nodeData = getNodeData(path);
        client.setData().withVersion(stat.getVersion()).forPath(path, value.getBytes());
        log.info("{} Znode data is set. old vaule is {}, new data is {}",path,nodeData,value);
    }


    /**
     * @Description: 创建 给定节点的监听事件  监听一个节点的更新和创建事件(不包括删除)
     * @param path
     * @Author: chenxue
     * @Date: 2020-08-22 16:47
     */
    public void addWatcherWithNodeCache(String path) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        // dataIsCompressed if true, data in the path is compressed
        NodeCache nodeCache = new NodeCache(client, path,false);
        NodeCacheListener listener = () -> {
            ChildData currentData = nodeCache.getCurrentData();
            log.info("{} Znode data is chagnge,new data is ---  {}", currentData.getPath(), new String(currentData.getData()));
        };
        nodeCache.getListenable().addListener(listener);
        nodeCache.start();
    }


    /**
     * @Description: 监听给定节点下的子节点的创建、删除、更新
     * @param path 给定节点
     * @Author: chenxue
     * @Date: 2020-08-22 17:14
     */
    public void addWatcherWithChildCache(String path) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        //cacheData if true, node contents are cached in addition to the stat
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,path,false);
        PathChildrenCacheListener listener = (client, event) -> {
            log.info("event path is --{} ,event type is {}" , event.getData().getPath(), event.getType());
        };
        pathChildrenCache.getListenable().addListener(listener);
        // StartMode : NORMAL  BUILD_INITIAL_CACHE  POST_INITIALIZED_EVENT
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }

    /**
     * @Description: 监听 给定节点的创建、更新（不包括删除） 以及 该节点下的子节点的创建、删除、更新动作。
     * @param path
     * @Author: chenxue
     * @Date: 2020-08-22 17:35
     */
    public void addWatcherWithTreeCache(String path){
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }

    }



}
