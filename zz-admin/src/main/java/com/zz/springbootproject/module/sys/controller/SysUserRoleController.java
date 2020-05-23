package com.zz.springbootproject.module.sys.controller;

import java.util.Arrays;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.utils.ServerResponse;
import com.zz.springbootproject.module.sys.service.SysUserRoleService;
import com.zz.springbootproject.module.sys.entity.SysUserRoleEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 用户与角色对应关系 前端控制器
 * @author chenxue
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/sys/sys-user-role-entity")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysUserRoleEntity:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = sysUserRoleService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysUserRoleEntity:info")
    public ServerResponse info(@PathVariable("id") Long id){
        SysUserRoleEntity sys_user_role = sysUserRoleService.getById(id);
        return ServerResponse.ok().put("sys_user_role", sys_user_role);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysUserRoleEntity:save")
    public ServerResponse save(@RequestBody SysUserRoleEntity sys_user_role){
        sysUserRoleService.save(sys_user_role);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysUserRoleEntity:update")
    public ServerResponse update(@RequestBody SysUserRoleEntity sys_user_role){
        sysUserRoleService.updateById(sys_user_role);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysUserRoleEntity:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        sysUserRoleService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
