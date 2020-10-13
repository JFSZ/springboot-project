package com.zz.springbootproject.demo.day3;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: throw 测试
 * @Author: chenxue
 * @Date: 2020-10-09 08:40
 */
@Slf4j
public class ThrowDemo {
    public static void main(String[] args) {
        ThrowDemo throwDemo = new ThrowDemo();
        throwDemo.test1();
    }

    public String test1(){
        try {
            test2();
        }catch (Exception e){
            log.info("test1");
            throw new RuntimeException("test1");
        }
        return "";
    }

    public String test2(){
        try {
            int a = 1/0;
        }catch (Exception e){
            log.info("test2");
            throw new RuntimeException("test2");
        }
        return "";
    }
}
