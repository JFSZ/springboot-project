package com.zz.util;

import org.junit.Test;

import java.util.function.Consumer;

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
    }
}
