package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
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
}
