package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.dao.SysMenuDao;
import com.zz.springbootproject.module.sys.dao.SysRoleDao;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.module.sys.dao.SysUserDao;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Resource
    private SysRoleDao sysRoleDao;
    @Resource
    private SysMenuDao sysMenuDao;
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysUserEntity> page = this.page(new Query<SysUserEntity>(params).getPage(),new QueryWrapper<SysUserEntity>());
      return new PageUtil(page);
   }

   /**
    * @Description: 根据用户登录id,查询用户权限
    * @param userId
    * @Author: chenxue
    * @Date: 2020/5/20  12:57
    * @return
    */
    @Override
    public List<String> queryPermById(Long userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new ServerException("未查询到当前登录用户信息!"));
        //查询登录人角色
        List<SysRoleEntity> roleList = sysRoleDao.queryByUserId(userId);
        Optional.ofNullable(roleList).orElseThrow(() -> new ServerException("未查询到当前登录用户角色信息!"));
        List<Long> collect = roleList.stream().map(SysRoleEntity::getRoleId).distinct().collect(Collectors.toList());
        //根据角色，查询菜单权限
        List<SysMenuEntity> menuList = sysMenuDao.queryByRoleId(collect);
        Optional.ofNullable(menuList).orElseThrow(() -> new ServerException("未查询到当前登录用户权限信息!"));
        List<String> perList = menuList.stream().map(SysMenuEntity::getPerms).distinct().collect(Collectors.toList());
        return perList;
    }

    /**
     * @Description: 根据用户名称，查询用户信息
     * @param username
     * @Author: chenxue
     * @Date: 2020/5/21  9:26
     */
    @Override
    public SysUserEntity queryByUserName(String username) {
        return null;
    }
}
