package com.zz.springbootproject.module.sys.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.zz.springbootproject.module.sys.service.SysMenuService;
import com.zz.springbootproject.module.sys.service.SysRoleMenuService;
import com.zz.springbootproject.module.sys.service.SysUserRoleService;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.validator.ValidatorUtils;
import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.module.sys.service.SysRoleService;
import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 前端控制器
 * @author chenxue
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
    * 列表
    */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = sysRoleService.queryPage(params);
        return page;
    }


    /**
    * 信息
    */
    @RequestMapping("/info")
    @RequiresPermissions("sys:role:info")
    public ServerResponse info(@RequestParam("roleId") Long roleId){
        Optional.ofNullable(roleId).orElseThrow(() ->new ServerException("参数不可为空!"));
        SysRoleEntity role = sysRoleService.getById(roleId);
        // 查询角色对应的菜单权限
        List<SysMenuEntity> menuEntityList = sysMenuService.queryByRoleId(roleId);
        role.setMenuIdList(Optional.ofNullable(menuEntityList)
                .orElse(new ArrayList<>())
                .stream()
                .map(SysMenuEntity::getMenuId)
                .collect(Collectors.toList()));
        return ServerResponse.ok().put("role", role);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    @Transactional
    public ServerResponse save(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role, AddGroup.class);
        return sysRoleService.saveOrUpdateRole(role);
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public ServerResponse update(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role, UpdateGroup.class);
        return sysRoleService.saveOrUpdateRole(role);
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public ServerResponse delete(@RequestBody List<String> ids){
        sysRoleService.removeByIds(ids);
        // 需要把角色、菜单表中的数据也删除掉。
        sysRoleMenuService.deleteRoleMenuByRoleId(ids);
        //删除角色、人员关系
        sysUserRoleService.deleteByRoleId(ids);
        return ServerResponse.ok();
    }

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    @RequestMapping("/queryByUserId")
    public ServerResponse queryByUserId(@RequestParam(value = "userId",defaultValue = "0") Long userId){
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.queryByUserId(userId);
        return ServerResponse.ok().put("list",sysRoleEntityList);
    }

}
