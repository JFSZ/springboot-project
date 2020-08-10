package com.zz.springbootproject.dataSource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 动态数据源 配置
 * @author: chenxue
 * @create: 2020-06-12 16:36
 **/
@Configuration
public class DynamicDataSourceConfig {

    /**
     * @param
     * @Description: 注册数据源
     * @Author: chenxue
     * @Date: 2020/6/15  18:11
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @param
     * @Description: 注册数据源
     * @Author: chenxue
     * @Date: 2020/6/15  18:11
     */
    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * @param
     * @Description: 注册动态路由
     * @Author: chenxue
     * @Date: 2020/6/15  18:11
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("firstDataSource") DataSource firstDataSource,
                                        @Qualifier("secondDataSource") DataSource secondDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicEnum.FIRSE.getName(), firstDataSource);
        targetDataSources.put(DynamicEnum.SECOND.getName(), secondDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}
