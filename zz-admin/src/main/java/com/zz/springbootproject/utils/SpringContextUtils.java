package com.zz.springbootproject.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-06-18 13:37
 **/
@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

}
