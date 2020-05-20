package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysRoleService extends IService<SysRoleEntity> {
 PageUtil queryPage(Map<String, Object> params);
}
