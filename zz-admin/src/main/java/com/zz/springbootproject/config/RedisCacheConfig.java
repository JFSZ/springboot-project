package com.zz.springbootproject.config;

import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.zz.springbootproject.module.sys.service.ConfigRedisService;
import com.zz.springbootproject.module.sys.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis 缓存启动自动加载配置
 * @author: chenxue
 * @create: 2020-06-30 11:07
 **/
@Configuration
@Slf4j
public class RedisCacheConfig implements InitializingBean {
    @Autowired
    private SysConfigService configService;
    @Autowired
    private ConfigRedisService configRedisService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("【启动时加载缓存模块】 -- 开始");
        long startTime = System.nanoTime();
        // 在加载bean之后，把参数配置加载到redis缓存中
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        List<SysConfigEntity> configEntityList = configService.listByMap(map);
        if (Objects.nonNull(configEntityList) && configEntityList.size() > 0) {
            configEntityList
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(o -> configRedisService.saveOrUpdate(o));
        }
        log.info("【启动时加载缓存模块】 -- 结束,加载缓存总用时：" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + "ms");
    }
}
