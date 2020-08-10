package com.zz.springbootproject.dataSource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;


/**
 * @description: 动态路由
 * @author: chenxue
 * @create: 2020-06-15 17:53
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public String getDataSource() {
        return contextHolder.get();
    }

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
