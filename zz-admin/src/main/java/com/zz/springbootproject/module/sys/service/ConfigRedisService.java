package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.zz.springbootproject.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @description: 系统参数redis操作service
 * @author: chenxue
 * @create: 2020-06-30 09:49
 **/
@Component
public class ConfigRedisService {
    @Autowired
    private RedisUtils redisUtils;


    /**
     * @param sysConfig
     * @Description: 新增或更新redis中参数缓存
     * @Author: chenxue
     * @Date: 2020/6/30  10:44
     */
    public void saveOrUpdate(SysConfigEntity sysConfig) {
        if (Objects.isNull(sysConfig)) {
            return;
        }
        //存入redis Map中
        redisUtils.hset(Constant.CONFIG_REDIS, sysConfig.getParamKey(), sysConfig.getParamValue());
    }

    /**
     * @param collect
     * @Description: 删除redis中参数缓存
     * @Author: chenxue
     * @Date: 2020/6/30  10:43
     */
    public void deleteByKeys(List<String> collect) {
        if (Objects.isNull(collect)) {
            return;
        }
        collect.stream().forEach(o -> redisUtils.hdel(Constant.CONFIG_REDIS, o));
    }

}
