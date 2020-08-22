package com.zz.util;

import com.google.gson.Gson;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import lombok.Data;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-05-11 17:15
 **/
public class UtilTest {
    private static Consumer c = System.out::println;

    @Test
    public void test() {
        c.accept(Math.ceil((double) 1 / 2));

        List<SysMenuEntity> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SysMenuEntity sysMenuEntity = new SysMenuEntity();
            sysMenuEntity.setPerms("sys:user:info,sys:user:list");
            sysMenuEntity.setPerms("sys:user:delete");
            list.add(sysMenuEntity);
        }
    }

    @Test
    public void test1() {

        System.out.println(System.currentTimeMillis() + TimeUnit.HOURS.toSeconds(1));
        System.out.println(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
    }

    @Test
    public void test2() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setId(String.valueOf(i));
            user.setName("Tom" + i);
            list.add(user);
        }
        Gson gson = new Gson();
        String s = gson.toJson(list.stream().map(User::getId).collect(Collectors.toList()));
        c.accept(s);
        List<User> list1 = new ArrayList<>();
        c.accept(gson.toJson(list1));
    }

    @Test
    public void test3() {
        System.out.println(System.getProperty("os.name"));
    }

    private String addr= "192.168.133.129:2181,192.168.133.129:2182,192.168.133.129:2183";

    private  int timeOut = 2000;

    private ZooKeeper zooKeeper = null;

    private String path = "/app";

    @Before
    public void init() throws IOException, InterruptedException {

        zooKeeper = new ZooKeeper(addr, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                List<String> children;
                try {
                    children = zooKeeper.getChildren("/",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        while (true) {
            if(ZooKeeper.States.CONNECTING == zooKeeper.getState()) {
                Thread.sleep(1000);
            }else {
                break;
            }
        }
    }

    // 创建节点
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String p = zooKeeper.create(path,"sk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println(p);
    }

    // 获取所有节点
    @Test
    public void getChild() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", true);
        children.stream().forEach(x -> System.out.println(x));
    }

    // 删除节点
    @Test
    public void deleteData() throws KeeperException, InterruptedException {
        zooKeeper.delete("/test",-1);
    }

    // 修改节点数据
    @Test
    public void setNode() throws KeeperException, InterruptedException {
        zooKeeper.setData("/root","hello world".getBytes(),-1);
        byte[] data = zooKeeper.getData("/root", false, new Stat());
        System.out.println(new String(data));

    }

    // 检查节点是否存在
    @Test
    public void checkNodeStatus() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/root", false);
        System.out.println(Objects.isNull(exists) ? "no exists" : " exists ");
    }




}

@Data
class User {
    private String Id;
    private String name;
}