package com.zz.springbootproject.dataSource.aspect;

import com.zz.springbootproject.dataSource.annotation.DS;
import com.zz.springbootproject.dataSource.config.DynamicDataSource;
import com.zz.springbootproject.dataSource.config.DynamicEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: 动态数据源切面
 * @author: chenxue
 * @create: 2020-06-15 18:17
 **/
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DSAspect implements Ordered {

    @Pointcut("@annotation(com.zz.springbootproject.dataSource.annotation.DS)")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            DS ds = method.getAnnotation(DS.class);
            if (ds == null) {
                DynamicDataSource.setDataSource(DynamicEnum.FIRSE.getName());
                log.info("设置动态数据源为: " + DynamicEnum.FIRSE.getName());
            } else {
                DynamicDataSource.setDataSource(ds.name());
                log.info("设置动态数据源为: " + ds.name());
            }
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            log.info("清除动态数据源");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
