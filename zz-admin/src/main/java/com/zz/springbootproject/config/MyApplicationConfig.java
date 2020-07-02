package com.zz.springbootproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: 自定义配置类
 * @author: chenxue
 * @create: 2020-07-02 19:25
 **/
@ConfigurationProperties("zzadmin")
@Component
public class MyApplicationConfig {
    private Token token;
    class Token{
        private String cacheType;
        private String expireTime;

        public String getCacheType() {
            return cacheType;
        }

        public void setCacheType(String cacheType) {
            this.cacheType = cacheType;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }
    }
}
