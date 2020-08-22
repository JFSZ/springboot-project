package com.zz.springbootproject.demo.day2;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;

import java.io.UnsupportedEncodingException;

/**
 * @description: Zookeeper 连接监听器
 * @author: chenxue
 * @create: 2020-08-22 14:06
 */
public class MyConnectionStateListener implements ConnectionStateListener {
    private String zkRegPathPrefix;
    private String regContent;

    public MyConnectionStateListener(String zkRegPathPrefix, String regContent) {
        this.zkRegPathPrefix = zkRegPathPrefix;
        this.regContent = regContent;
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if(connectionState == ConnectionState.LOST){
            while (true) {
                try {
                    if(curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()){
                        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                                .forPath(zkRegPathPrefix,regContent.getBytes("UTF-8"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
