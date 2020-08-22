package com.zz.springbootproject.demo.day2;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @Description: ZK denmo
 * @Author: chenxue
 * @Date: 2020-08-19 17:30
 */
@Slf4j
public class ZKDemo {
    String addr= "192.168.133.129:2181,192.168.133.129:2182,192.168.133.129:2183";
    int timeOut = 2000;
    String server_path = "/test";
    ZooKeeper zooKeeper = null;
    ZooKeeper finalZk = zooKeeper;
    public static void main(String[] args) {
        ZKDemo zkDemo = new ZKDemo();
        zkDemo.register("root");

    }

    public void register(String address){
        try {
            zooKeeper = new ZooKeeper(addr, timeOut, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    List<String> children = null;
                    try {
                        children = finalZk.getChildren(server_path,true);
                        children.stream().forEach(System.out::println);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 只有连接上，才可以创建目录
            while (true) {
                if(ZooKeeper.States.CONNECTING == zooKeeper.getState()) {
                    Thread.sleep(1000);
                }else {
                    break;
                }
            }
            log.info("ZK status is {}",zooKeeper.getState());
            String path = address;
            /**
             *CreateMode类型分为4种
             * 1.PERSISTENT--持久型
             * 2.PERSISTENT_SEQUENTIAL--持久顺序型
             * 3.EPHEMERAL--临时型
             * 4.EPHEMERAL_SEQUENTIAL--临时顺序型
             *
             *
             * Zookeeper ZooDefs.Ids
             *
             * OPEN_ACL_UNSAFE  : 完全开放的ACL，任何连接的客户端都可以操作该属性znode
             * CREATOR_ALL_ACL : 只有创建者才有ACL权限
             * READ_ACL_UNSAFE：只能读取ACL
             */
            zooKeeper.create(server_path,path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
