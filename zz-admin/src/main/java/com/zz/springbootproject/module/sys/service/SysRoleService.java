package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 角色 服务类
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysRoleService extends IService<SysRoleEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * 根据用户查询角色
     *
     * @param userId
     * @return
     */
    List<SysRoleEntity> queryByUserId(Long userId);

    /**
     * @param role
     * @Description: 新增/更新角色
     * @Author: chenxue
     * @Date: 2020/6/4  18:53
     */
    ServerResponse saveOrUpdateRole(SysRoleEntity role);

}
