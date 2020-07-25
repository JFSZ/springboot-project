package com.zz.util;

import com.google.gson.Gson;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public void test(){
        c.accept(Math.ceil((double) 1/2));

        List<SysMenuEntity> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SysMenuEntity sysMenuEntity = new SysMenuEntity();
            sysMenuEntity.setPerms("sys:user:info,sys:user:list");
            sysMenuEntity.setPerms("sys:user:delete");
            list.add(sysMenuEntity);
        }
    }
    @Test
    public void test1(){

        System.out.println(System.currentTimeMillis() + TimeUnit.HOURS.toSeconds(1));
        System.out.println(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
    }

    @Test
    public void test2(){
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
    public void test3(){
        System.out.println(System.getProperty("os.name"));
    }
}
@Data
class User{
    private String Id;
    private String name;
}