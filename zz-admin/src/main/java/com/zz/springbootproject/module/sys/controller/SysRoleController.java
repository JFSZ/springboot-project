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
import com.zz.springbootproject.module.sys.service.SysRoleService;
import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/sys/sys-role-entity")
public class SysRoleController {
    @Autowired
    private SysRoleService ysRoleService;

    /**
    * 列表
    */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysRoleEntity:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = ysRoleService.queryPage(params);
        return page;
    }


    /**
    * 信息
    */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysRoleEntity:info")
    public ServerResponse info(@PathVariable("id") Long id){
        SysRoleEntity sys_role = ysRoleService.getById(id);
        return ServerResponse.ok().put("sys_role", sys_role);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysRoleEntity:save")
    public ServerResponse save(@RequestBody SysRoleEntity sys_role){
        ysRoleService.save(sys_role);
        return ServerResponse.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysRoleEntity:update")
    public ServerResponse update(@RequestBody SysRoleEntity sys_role){
        ysRoleService.updateById(sys_role);
        return ServerResponse.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysRoleEntity:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        ysRoleService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
