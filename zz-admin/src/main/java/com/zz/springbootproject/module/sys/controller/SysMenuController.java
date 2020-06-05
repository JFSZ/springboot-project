package com.zz.springbootproject.module.sys.controller;

import java.util.*;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.zz.springbootproject.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
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
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = sysMenuService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:menu:info")
    public ServerResponse info(@PathVariable("id") Long id){
        SysMenuEntity sys_menu = sysMenuService.getById(id);
        return ServerResponse.ok().put("sys_menu", sys_menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public ServerResponse save(@RequestBody SysMenuEntity sys_menu){
        sysMenuService.save(sys_menu);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public ServerResponse update(@RequestBody SysMenuEntity sys_menu){
        sysMenuService.updateById(sys_menu);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        sysMenuService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

    /**
     * @Description: 获取导航栏菜单
     * @param
     * @Author: chenxue
     * @Date: 2020/5/22  17:21
     */
    @RequestMapping("/nav")
    public ServerResponse nav(){
        SysUserEntity user = ShiroUtils.getUser();
        //查询登录人的权限和菜单
        List<SysMenuEntity> menulist = sysMenuService.queryByUserId(user.getUserId());
        List<String> permList = sysUserService.queryPermById(user.getUserId());
        Set<String> permSet = new HashSet<>();
        permList.stream().filter(Objects::nonNull).forEach(o -> permSet.addAll(Arrays.asList(o.trim().split(","))));
        return ServerResponse.ok().put("menuList",menulist).put("permissions",permSet);
    }

    /**
     * @Description: 根据角色查询菜单权限
     * @param roleId 角色id
     * @Author: chenxue
     * @Date: 2020/6/5  17:12
     */
    @RequestMapping("/queryByRoleId")
    public ServerResponse queryByRoleId(String roleId){
        Optional.ofNullable(roleId).orElseThrow(() -> new ServerException("参数为空!"));
        return sysMenuService.queryByRoleId(roleId);
    }

}
