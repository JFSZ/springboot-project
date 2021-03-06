package com.zz.springbootproject.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @description: 自定义配置类
 * @author: chenxue
 * @create: 2020-07-02 19:25
 **/
@ConfigurationProperties(prefix = "zzadmin.token")
@Configuration
@Data
public class TokenConfig {
    /**
     * 缓存方式 redis 缓存 db缓存 默认redis缓存
     */
    private String cacheType = CacheEnum.REDIS.getName();
    private Duration expireTime = Duration.ofSeconds(-1L);

    @Override
    public String toString() {
        return "Token{" +
                "cacheType='" + cacheType + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }

    public enum CacheEnum {
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
