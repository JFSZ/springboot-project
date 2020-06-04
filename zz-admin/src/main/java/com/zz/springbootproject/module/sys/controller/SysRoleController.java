package com.zz.springbootproject.module.sys.controller;

import java.util.Arrays;
import java.util.List;
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
 * 角色 前端控制器
 * @author chenxue
 * @since 2020-05-20
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

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
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public ServerResponse info(@PathVariable("roleId") Long roleId){
        SysRoleEntity role = sysRoleService.getById(roleId);
        return ServerResponse.ok().put("role", role);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public ServerResponse save(@RequestBody SysRoleEntity role){
        return sysRoleService.saveRole(role);
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public ServerResponse update(@RequestBody SysRoleEntity role){
        sysRoleService.updateById(role);
        return ServerResponse.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public ServerResponse delete(@RequestBody List<String> ids){
        sysRoleService.removeByIds(ids);
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
