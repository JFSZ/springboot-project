package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统配置信息表 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-06-29
 */
public interface SysConfigService extends IService<SysConfigEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @param sysConfig
     * @Description: 保存配置参数
     * @Author: chenxue
     * @Date: 2020/6/30  10:29
     */
    ServerResponse saveConfig(SysConfigEntity sysConfig);

    /**
     * @param sysConfig
     * @Description: 更新配置参数
     * @Author: chenxue
     * @Date: 2020/6/30  10:29
     */
    ServerResponse updateConfig(SysConfigEntity sysConfig);

    /**
     * @param ids
     * @Description: 删除配置参数
     * @Author: chenxue
     * @Date: 2020/6/30  10:29
     */
    ServerResponse deleteConfig(List<Long> ids);
}
