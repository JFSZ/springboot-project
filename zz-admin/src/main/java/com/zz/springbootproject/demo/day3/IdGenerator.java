package com.zz.springbootproject.demo.day3;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 1、 没锁的情况，会导致有重复id产生
 * 2、加锁 lock
 * @author: chenxue
 * @create: 2020-08-22 10:41
 */
public class IdGenerator {
    private int index = 0;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd-HH-mm-ss-");
    private Lock lock = new ReentrantLock();

    public String createId() {
        try{
            lock.lock();
            String format = df.format(LocalDateTime.now());
            String s = "" + (++index);
            System.out.println(s);
            return s;
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        int test = test();
        System.out.println(test);
    }

    public static int test(){
        int b = 1;
        int c = 2;
        try {
            int a = 1/1;
        }catch (Exception e){
            return b;
        }finally {
            return c;
        }
    }

}
