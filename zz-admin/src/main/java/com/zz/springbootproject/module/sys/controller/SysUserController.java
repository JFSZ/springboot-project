package com.zz.springbootproject.module.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.zz.springbootproject.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zz.springbootproject.utils.ServerResponse;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.utils.PageUtil;

/**
 * 系统用户 前端控制器
 * @author chenxue
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysUserService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public ServerResponse info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);
        return ServerResponse.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public ServerResponse save(@RequestBody SysUserEntity sys_user) {
        sysUserService.save(sys_user);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public ServerResponse update(@RequestBody SysUserEntity sys_user) {
        sysUserService.updateById(sys_user);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public ServerResponse delete(@RequestParam Long[] ids) {
        sysUserService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

    @GetMapping("/info")
    public ServerResponse info() {
        return ServerResponse.ok().put("user", ShiroUtils.getUser());
    }
}
