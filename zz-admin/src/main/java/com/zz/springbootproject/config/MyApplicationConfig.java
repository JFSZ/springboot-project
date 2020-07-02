package com.zz.springbootproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @description: 自定义配置类
 * @author: chenxue
 * @create: 2020-07-02 19:25
 **/
@ConfigurationProperties(prefix = "zzadmin")
@Component
public class MyApplicationConfig {
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static class Token{
        /**
         * 缓存方式 redis 缓存 db缓存 默认redis缓存
         */
        private String cacheType = CacheEnum.REDIS.getName();
        private Duration expireTime = Duration.ofSeconds(-1L);

        public String getCacheType() {
            return cacheType;
        }

        public void setCacheType(String cacheType) {
            this.cacheType = cacheType;
        }

        public Duration getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(Duration expireTime) {
            this.expireTime = expireTime;
        }
    }
    public enum CacheEnum{
        REDIS("redis"),
        DB("db");
        private String name;

        CacheEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
