package com.zz.springbootproject.util;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @description: 基于jdk 8 的日期操作工具类
 * @author: chenxue
 * @create: 2020-07-14 10:10
 **/
public class DateUtil {
    /**
     * @param begin
     * @param end
     * @Description: 判断当前日期与给定日期相差天数
     * @Author: chenxue
     * @Date: 2020/7/13  18:50
     */
    public static long getDiffDay(Date begin, Date end) {
        LocalDate beginDate = date2LocalDate(begin);
        LocalDate endDate = date2LocalDate(end);
        return endDate.toEpochDay() - beginDate.toEpochDay();
    }

    /**
     * @param date
     * @Description: Date 转 LocalDate
     * @Author: chenxue
     * @Date: 2020/7/14  9:56
     */
    public static LocalDate date2LocalDate(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param localDate
     * @Description: LocalDate 转 Date
     * @Author: chenxue
     * @Date: 2020/7/14  9:56
     */
    public static Date localDate2Date(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static void main(String[] args) throws InterruptedException {
        LocalDate date = LocalDate.now();
        System.out.println(date);
        Thread.sleep(1000);
        LocalDate localDate = date2LocalDate(new Date());
        System.out.println(date2LocalDate(new Date()));
        System.out.println(date.equals(localDate));
    }
}
