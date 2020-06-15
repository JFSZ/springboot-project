package com.zz.springbootproject.dataSource.config;

/**
 * @description: 动态数据源枚举
 * @author: chenxue
 * @create: 2020-06-15 18:06
 **/
public enum DynamicEnum {
    FIRSE("first","主数据库"),
    SECOND("second","从数据库");
    private String name;
    private String value;

    DynamicEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
