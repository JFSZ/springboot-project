package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysRoleMenuEntity;
import com.zz.springbootproject.module.sys.dao.SysRoleMenuDao;
import com.zz.springbootproject.module.sys.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import java.util.*;

/**
 * 角色与菜单对应关系 服务实现类
 *
 * @author chenxue
 * @since 2020-06-09
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(new Query<SysRoleMenuEntity>(params).getPage(), new QueryWrapper<SysRoleMenuEntity>());
        return new PageUtil(page);
    }

    /**
     * @param roleId
     * @param menuIdList
     * @Description: 保存/更新 角色权限表
     * @Author: chenxue
     * @Date: 2020/6/9  15:13
     */
    @Override
    public boolean saveOrUpdateByRole(Long roleId, List<Long> menuIdList) {
        // 先删除原有角色、菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("role_id", roleId);
        this.removeByMap(map);
        List<SysRoleMenuEntity> list = new ArrayList<>();
        for (Long menuId : menuIdList) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setRoleId(roleId);
            sysRoleMenuEntity.setMenuId(menuId);
            list.add(sysRoleMenuEntity);
        }
        if (list.size() > 0) {
            return this.saveOrUpdateBatch(list);
        }
        return true;
    }

    /**
     * @param ids
     * @Description: 根据角色 删除菜单、角色关系
     * @Author: chenxue
     * @Date: 2020/6/9  17:14
     */
    @Override
    public void deleteRoleMenuByRoleId(List<String> ids) {
        baseMapper.deleteRoleMenuByRoleId(ids);
    }

    /**
     * @param ids
     * @Description: 根据菜单 删除菜单、角色关系
     * @Author: chenxue
     * @Date: 2020/6/12  10:32
     */
    @Override
    public void deleteRoleMenuByMenuId(List<String> ids) {
        baseMapper.deleteRoleMenuByMenuId(ids);
    }
}
