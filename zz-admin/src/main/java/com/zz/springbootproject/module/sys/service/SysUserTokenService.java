package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysUserTokenEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * <p>
 * 系统用户Token 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {
     PageUtil queryPage(Map<String, Object> params);
}
