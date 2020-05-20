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
import com.zz.springbootproject.module.sys.service.SysMenuService;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理 前端控制器
 * @author chenxue
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/sys/sys-menu-entity")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysMenuEntity:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = sysMenuService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysMenuEntity:info")
    public ServerResponse info(@PathVariable("id") Long id){
        SysMenuEntity sys_menu = sysMenuService.getById(id);
        return ServerResponse.ok().put("sys_menu", sys_menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysMenuEntity:save")
    public ServerResponse save(@RequestBody SysMenuEntity sys_menu){
        sysMenuService.save(sys_menu);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysMenuEntity:update")
    public ServerResponse update(@RequestBody SysMenuEntity sys_menu){
        sysMenuService.updateById(sys_menu);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysMenuEntity:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        sysMenuService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
