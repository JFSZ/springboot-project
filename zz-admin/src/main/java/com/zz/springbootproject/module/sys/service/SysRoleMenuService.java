package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系 服务类
 * @author chenxue
 * @since 2020-06-09
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @Description: 保存/更新 角色权限表
     * @param roleId
     * @param menuIdList
     * @Author: chenxue
     * @Date: 2020/6/9  15:13
     */
    boolean saveOrUpdateByRole(Long roleId, List<Long> menuIdList);

    /**
     * @Description: 删除菜单、角色关系
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/9  17:14
     */
    void deleteRoleMenu(List<String> ids);
}
