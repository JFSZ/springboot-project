package com.zz.springbootproject.dataSource.annotation;

import java.lang.annotation.*;

/**
 * @description: 动态数据源注解
 * @author: chenxue
 * @create: 2020-06-15 18:16
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {
    String name() default "";
}
