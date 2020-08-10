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
import com.zz.springbootproject.module.sys.service.SysUserTokenService;
import com.zz.springbootproject.module.sys.entity.SysUserTokenEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 系统用户Token 前端控制器
 *
 * @author chenxue
 * @since 2020-05-22
 */
@RestController
@RequestMapping("/sys/sys-user-token-entity")
public class SysUserTokenController {
    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysUserTokenEntity:list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysUserTokenService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysUserTokenEntity:info")
    public ServerResponse info(@PathVariable("id") Long id) {
        SysUserTokenEntity sys_user_token = sysUserTokenService.getById(id);
        return ServerResponse.ok().put("sys_user_token", sys_user_token);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysUserTokenEntity:save")
    public ServerResponse save(@RequestBody SysUserTokenEntity sys_user_token) {
        sysUserTokenService.save(sys_user_token);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysUserTokenEntity:update")
    public ServerResponse update(@RequestBody SysUserTokenEntity sys_user_token) {
        sysUserTokenService.updateById(sys_user_token);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysUserTokenEntity:delete")
    public ServerResponse delete(@RequestBody Long[] ids) {
        sysUserTokenService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
