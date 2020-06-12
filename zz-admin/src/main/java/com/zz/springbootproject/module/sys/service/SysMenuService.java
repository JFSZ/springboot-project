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
    List<SysMenuEntity> queryByRoleId(Long roleId);

    /**
     * @Description: 查找不是按钮的菜单
     * @param
     * @Author: chenxue
     * @Date: 2020/6/10  14:24
     */
    List<SysMenuEntity> queryNotButtonMenuList();

    /**
     * @Description: 保存、更新菜单
     * @param sysMenu
     * @Author: chenxue
     * @Date: 2020/6/12  10:13
     */
    ServerResponse saveOrUpdateMenu(SysMenuEntity sysMenu);
}
