package com.zz.springbootproject.module.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.module.sys.service.SysRoleService;
import com.zz.springbootproject.module.sys.service.SysUserRoleService;
import com.zz.springbootproject.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
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
    @RequestMapping("/info")
    @RequiresPermissions("sys:user:info")
    public ServerResponse info(@RequestParam("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        List<SysRoleEntity> sysRoleEntityList = sysRoleService.queryByUserId(userId);
        user.setRoleIdList(Optional.ofNullable(sysRoleEntityList)
                .orElse(new ArrayList<>())
                .stream()
                .map(SysRoleEntity::getRoleId)
                .collect(Collectors.toList()));
        return ServerResponse.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    @Transactional
    public ServerResponse save(@RequestBody SysUserEntity user) {
        sysUserService.saveUser(user);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public ServerResponse update(@RequestBody SysUserEntity user) {
        Optional.ofNullable(user).map(x -> x.getUserId()).orElseThrow(() -> new ServerException("参数缺失!"));
        sysUserService.updateById(user);
        //更新角色
        sysUserRoleService.saveOrUpdateByParam(user.getUserId(),user.getRoleIdList());
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public ServerResponse delete(@RequestBody List<Long> userIds) {
        sysUserService.removeByIds(userIds);
        // 删除角色、用户表数据
        sysUserRoleService.deleteByUserId(userIds);
        return ServerResponse.ok();
    }

    @GetMapping("/getUserInfo")
    public ServerResponse getUserInfo() {
        return ServerResponse.ok().put("user", ShiroUtils.getUser());
    }
}
