package com.zz.util;

import org.junit.Test;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-08-27 13:35
 */
public class MainTest {
    @Test
    public void demo1(){
        // 斐波那函数

        int a = 1,b = 1;
        for (int i = 0; i < 10; i++) {
            System.out.println(a + "," + b);
            a = a + b;
            b = a + b;

        }
    }


}
