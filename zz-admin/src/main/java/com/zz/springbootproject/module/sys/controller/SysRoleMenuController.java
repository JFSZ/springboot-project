package com.zz.springbootproject.module.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.zz.springbootproject.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.module.sys.service.SysRoleMenuService;
import com.zz.springbootproject.module.sys.entity.SysRoleMenuEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 角色与菜单对应关系 前端控制器
 *
 * @author chenxue
 * @since 2020-06-09
 */
@RestController
@RequestMapping("/sys/role/menuy")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:roleMenu:list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysRoleMenuService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:roleMenu:info")
    public ServerResponse info(@PathVariable("id") Long id) {
        SysRoleMenuEntity roleMenu = sysRoleMenuService.getById(id);
        return ServerResponse.ok().put("roleMenu", roleMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:roleMenu:save")
    public ServerResponse save(@RequestBody SysRoleMenuEntity roleMenu) {
        sysRoleMenuService.save(roleMenu);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:roleMenu:update")
    public ServerResponse update(@RequestBody SysRoleMenuEntity roleMenu) {
        sysRoleMenuService.updateById(roleMenu);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:roleMenu:delete")
    public ServerResponse delete(@RequestBody Long[] ids) {
        sysRoleMenuService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
