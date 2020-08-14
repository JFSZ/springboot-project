package com.zz.springbootproject.demo.day1;


import java.util.Arrays;

/**
 * @description: 算法排序问题 假设 list
 * 1、随机给定数，按照指定顺序排序
 * @author: chenxue
 * @create: 2020-08-14 16:11
 **/
public class SortDemo {
    // 桶排序
    public static void main(String[] args) {
        int book[] = {5,8,2,3,1,2,5,9};
        int[] t = new int [book.length + 1];
        for (int i = 0; i < t.length; i++) {
            for (int j = i; j < book.length; j++) {
                if(book[j] == i) {
                    t[i] ++;
                }
            }
        }

        for (int i = 0; i < t.length; i++) {
            if(t[i] != 0){
                System.out.println(t[i]);
            }
        }
    }
}


