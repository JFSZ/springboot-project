package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.module.sys.dao.SysRoleDao;
import com.zz.springbootproject.module.sys.entity.SysRoleMenuEntity;
import com.zz.springbootproject.module.sys.service.SysRoleMenuService;
import com.zz.springbootproject.module.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.springbootproject.utils.ServerResponse;
import com.zz.springbootproject.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import java.util.*;

/**
 * 角色 服务实现类
 * @author chenxue
 * @since 2020-05-20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysRoleEntity> page = new Query<SysRoleEntity>(params).getPage();
      List<SysRoleEntity> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRoleEntity> queryByUserId(Long userId) {
        List<SysRoleEntity> sysRoleEntityList = baseMapper.queryByUserId(userId);
        return sysRoleEntityList;
    }

    /**
     * @Description: 新增、更新角色
     * @param role
     * @Author: chenxue
     * @Date: 2020/6/4  18:53
     */
    @Override
    public ServerResponse saveOrUpdateRole(SysRoleEntity role) {
        if(Objects.isNull(role.getRoleId())){
            role.setCreateTime(new Date());
            role.setCreateUserId(ShiroUtils.getUser().getUserId());
        }
        this.saveOrUpdate(role);
        // 保存角色、菜单关系
        boolean flag = sysRoleMenuService.saveOrUpdateByRole(role.getRoleId(),role.getMenuIdList());
        if(flag){
            return ServerResponse.ok();
        }
        return ServerResponse.error();
    }

}
