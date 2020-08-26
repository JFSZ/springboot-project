package com.zz.springbootproject.demo.day3;


/**
 * @description: TODO
 * @author: chenxue
 * @create: 2020-08-22 16:31
 */
public class CuratorTest {
    public static void main(String[] args) throws Exception {
        CuratorUtil curatorUtil = new CuratorUtil("curator");
        String test = curatorUtil.createNode("/app/test", "test");
        curatorUtil.addWatcherWithTreeCache(test);
        System.in.read();
    }



}
