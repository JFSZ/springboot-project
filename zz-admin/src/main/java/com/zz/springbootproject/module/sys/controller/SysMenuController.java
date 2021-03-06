package com.zz.springbootproject.module.sys.controller;

import java.util.*;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.module.sys.service.SysRoleMenuService;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.ShiroUtils;
import com.zz.springbootproject.validator.ValidatorUtils;
import com.zz.springbootproject.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.module.sys.service.SysMenuService;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;

import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理 前端控制器
 *
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

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * @param params
     * @Description: 根据角色查询菜单权限
     * @Author: chenxue
     * @Date: 2020/6/5  17:12
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    // @DS(name = "second")
    public ServerResponse list(@RequestParam Map<String, Object> params) {
        List<SysMenuEntity> list = sysMenuService.list();
        for (SysMenuEntity sysMenuEntity : list) {
            if (Objects.nonNull(sysMenuEntity)) {
                SysMenuEntity entity = sysMenuService.getById(sysMenuEntity.getParentId());
                if (Objects.nonNull(entity)) {
                    sysMenuEntity.setParentName(entity.getName());
                }
            }
        }
        return ServerResponse.ok().put("menuList", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:menu:info")
    public ServerResponse info(@PathVariable("id") Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new ServerException("参数为空"));
        SysMenuEntity sysMenu = sysMenuService.getById(id);
        if (Objects.nonNull(sysMenu)) {
            SysMenuEntity sysMenuEntity = sysMenuService.getById(sysMenu.getParentId());
            if (Objects.nonNull(sysMenuEntity)) {
                sysMenu.setParentName(sysMenuEntity.getName());
            }
        }
        return ServerResponse.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    // @DS
    public ServerResponse save(@RequestBody SysMenuEntity sysMenu) {
        ValidatorUtils.validateEntity(sysMenu, AddGroup.class);
        return sysMenuService.saveOrUpdateMenu(sysMenu);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public ServerResponse update(@RequestBody SysMenuEntity sysMenu) {
        ValidatorUtils.validateEntity(sysMenu, AddGroup.class);
        return sysMenuService.saveOrUpdateMenu(sysMenu);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public ServerResponse delete(@RequestBody List<String> ids) {
        sysMenuService.removeByIds(ids);
        // 删除 角色、菜单
        sysRoleMenuService.deleteRoleMenuByMenuId(ids);
        return ServerResponse.ok();
    }

    /**
     * @param
     * @Description: 获取导航栏菜单
     * @Author: chenxue
     * @Date: 2020/5/22  17:21
     */
    @RequestMapping("/nav")
    public ServerResponse nav() {
        SysUserEntity user = ShiroUtils.getUser();
        //查询登录人的权限和菜单
        List<SysMenuEntity> menulist = sysMenuService.queryByUserId(user.getUserId());
        List<String> permList = sysUserService.queryPermById(user.getUserId());
        Set<String> permSet = new HashSet<>();
        permList.stream().filter(Objects::nonNull).forEach(o -> permSet.addAll(Arrays.asList(o.trim().split(","))));
        return ServerResponse.ok().put("menuList", menulist).put("permissions", permSet);
    }

    /**
     * @param
     * @Description: 查找不是按钮的菜单
     * @Author: chenxue
     * @Date: 2020/6/10  14:24
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:list")
    public ServerResponse select() {
        List<SysMenuEntity> list = sysMenuService.queryNotButtonMenuList();
        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        list.add(root);
        return ServerResponse.ok().put("menuList", list);
    }

}
