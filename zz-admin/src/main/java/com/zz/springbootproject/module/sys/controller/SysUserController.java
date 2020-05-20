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
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户 前端控制器
 * @author chenxue
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/sys/sys-user-entity")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysUserEntity:list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysUserService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysUserEntity:info")
    public ServerResponse info(@PathVariable("id") Long id) {
        SysUserEntity sys_user = sysUserService.getById(id);
        return ServerResponse.ok().put("sys_user", sys_user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysUserEntity:save")
    public ServerResponse save(@RequestBody SysUserEntity sys_user) {
        sysUserService.save(sys_user);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysUserEntity:update")
    public ServerResponse update(@RequestBody SysUserEntity sys_user) {
        sysUserService.updateById(sys_user);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysUserEntity:delete")
    public ServerResponse delete(@RequestBody Long[] ids) {
        sysUserService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
