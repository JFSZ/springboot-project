package com.zz.springbootproject.demo.day3;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

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
    public static CuratorFramework client;
    // zk 服务端集群地址
    private String connectString = "192.168.133.129:2181,192.168.133.129:2182,192.168.133.129:2183";

    // session 超时时间
    private int timeOut = 60000;

    // zkclient 重试间隔时间
    private int baseSleepTimeMs = 5000;

    //zkclient 重试次数
    private int retryCount = 5;

    public CuratorUtil() {
        this(null);
    }

    public CuratorUtil(String path) {
        init(path);
    }

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    // zk连接状态监听器.用于丢失连接时重新连接。
    private ConnectionStateListener connectionStateListener = new ConnectionStateListener() {
        @Override
        public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
            if(connectionState == ConnectionState.CONNECTED){
                log.info("the client is connected...");
                countDownLatch.countDown();
            }else if(connectionState == ConnectionState.LOST){
                log.info("the client is lost connect, retry to connect...");
                retryConnect();
                log.info("the client is success reconnect...");
            }
        }
    };
    /**
     * @param
     * @Description: 创建客户端
     * retryPolicy，重试连接策略，有四种实现
     * ExponentialBackoffRetry（重试指定的次数, 且每一次重试之间停顿的时间逐渐增加）、
     * RetryNtimes（指定最大重试次数的重试策略）、
     * RetryOneTimes（仅重试一次）、
     * RetryUntilElapsed（一直重试直到达到规定的时间）
     * @Author: chenxue
     * @Date: 2020-08-22 14:57
     */
    public void init(String path) {
        if (null == client) {
            synchronized (CuratorUtil.this) {
                if (null == client) {
                    client = CuratorFrameworkFactory
                            .builder()
                            .connectString(connectString)
                            .sessionTimeoutMs(timeOut)
                            .retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, retryCount))
                            .namespace(path)
                            .build();
                    client.start();
                    log.info("client is created at ================== {}", LocalDateTime.now());
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    public void retryConnect(){
        // 首先检查连接是否关闭
        closeConnection();
        init("");
    }


    /**
     * @param path
     * @param value
     * @Description: 创建节点
     * @Author: chenxue
     * @Date: 2020-08-22 15:15
     */
    public String createNode(String path, String value) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        String node = client
                .create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL) // 临时顺序节点
                .forPath(path, value.getBytes());

        log.info("create node : {}", node);
        return node;
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
    public void addWatcherWithTreeCache(String path) throws Exception {
        if (null == client) {
            throw new RuntimeException("there is not connect to zkServer...");
        }
        TreeCache treeCache = new TreeCache(client, path);
        TreeCacheListener listener = (client, event) -> {
            log.info("节点路径 --{} ,节点事件类型: {} , 节点值为: {}" , Objects.nonNull(event.getData()) ? event.getData().getPath() : "无数据", event.getType());
        };
        treeCache.getListenable().addListener(listener);
        treeCache.start();
    }





}
