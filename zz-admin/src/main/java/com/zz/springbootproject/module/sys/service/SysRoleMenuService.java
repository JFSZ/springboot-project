package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系 服务类
 *
 * @author chenxue
 * @since 2020-06-09
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @param roleId
     * @param menuIdList
     * @Description: 保存/更新 角色权限表
     * @Author: chenxue
     * @Date: 2020/6/9  15:13
     */
    boolean saveOrUpdateByRole(Long roleId, List<Long> menuIdList);

    /**
     * @param ids
     * @Description: 根据角色 删除菜单、角色关系
     * @Author: chenxue
     * @Date: 2020/6/9  17:14
     */
    void deleteRoleMenuByRoleId(List<String> ids);

    /**
     * @param ids
     * @Description: 根据菜单 删除菜单、角色关系
     * @Author: chenxue
     * @Date: 2020/6/12  10:32
     */
    void deleteRoleMenuByMenuId(List<String> ids);
}
