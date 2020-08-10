package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.zz.springbootproject.module.sys.dao.SysConfigDao;
import com.zz.springbootproject.module.sys.service.ConfigRedisService;
import com.zz.springbootproject.module.sys.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统配置信息表 服务实现类
 *
 * @author chenxue
 * @since 2020-06-29
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {


    @Autowired
    private ConfigRedisService configRedisService;

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<SysConfigEntity> page = new Query<SysConfigEntity>(params).getPage();
        List<SysConfigEntity> list = baseMapper.queryPage(page, params);
        return new PageUtil(page.setRecords(list));
    }

    /**
     * @param sysConfig
     * @Description: 保存配置参数, 并存入缓存中
     * @Author: chenxue
     * @Date: 2020/6/30  9:20
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse saveConfig(SysConfigEntity sysConfig) {
        //key 不可以重复
        SysConfigEntity sysConfigEntity = baseMapper.queryByParamKey(sysConfig.getParamKey());
        if (Objects.nonNull(sysConfigEntity)) {
            return ServerResponse.error("参数名不可重复!");
        }
        // 保存参数对象
        sysConfig.setStatus(1);
        sysConfig.setCreateTime(new Date());
        sysConfig.setCreateUser(ShiroUtils.getUser().getUserId());
        boolean save = this.save(sysConfig);
        if (save) {
            //存入redis缓存中。以Map方式存储
            configRedisService.saveOrUpdate(sysConfig);
            return ServerResponse.ok();
        }
        return ServerResponse.error();
    }

    /**
     * @param sysConfig
     * @Description: 更新配置参数
     * @Author: chenxue
     * @Date: 2020/6/30  10:29
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse updateConfig(SysConfigEntity sysConfig) {
        boolean flag = this.updateById(sysConfig);
        if (flag) {
            //更新redis缓存
            configRedisService.saveOrUpdate(sysConfig);
            return ServerResponse.ok();
        }
        return ServerResponse.error();
    }

    /**
     * @param ids
     * @Description: 删除配置参数
     * @Author: chenxue
     * @Date: 2020/6/30  10:29
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse deleteConfig(List<Long> ids) {
        List<SysConfigEntity> list = this.listByIds(ids);
        Optional.ofNullable(list).orElseThrow(() -> new ServerException("参数不可为空!"));
        List<String> collect = list.stream().filter(Objects::nonNull).map(SysConfigEntity::getParamKey).collect(Collectors.toList());
        boolean falg = this.removeByIds(ids);
        if (falg) {
            // 删除缓存中的key
            configRedisService.deleteByKeys(collect);
            return ServerResponse.ok();
        }
        return ServerResponse.error();
    }
}
