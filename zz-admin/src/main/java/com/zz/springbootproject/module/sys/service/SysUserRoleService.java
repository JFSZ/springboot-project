package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysUserRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-23
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {
     PageUtil queryPage(Map<String, Object> params);
}
