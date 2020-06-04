package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import com.zz.springbootproject.utils.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * 角色 服务类
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysRoleService extends IService<SysRoleEntity> {
 PageUtil queryPage(Map<String, Object> params);

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    List<SysRoleEntity> queryByUserId(Long userId);

    /**
     * @Description: 新增角色
     * @param role
     * @Author: chenxue
     * @Date: 2020/6/4  18:53
     */
    ServerResponse saveRole(SysRoleEntity role);
}
