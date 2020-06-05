package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import com.zz.springbootproject.utils.ServerResponse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysMenuService extends IService<SysMenuEntity> {
    PageUtil queryPage(Map<String, Object> params);

    List<SysMenuEntity> queryByUserId(Long userId);

    /**
     * @Description: 根据角色查询菜单权限
     * @param roleId 角色id
     * @Author: chenxue
     * @Date: 2020/6/5  17:12
     */
    ServerResponse queryByRoleId(String roleId);
}
