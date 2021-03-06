package com.zz.springbootproject.module.sys.controller;

import java.util.List;
import java.util.Map;

import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.validator.ValidatorUtils;
import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.module.sys.service.SysConfigService;
import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置信息表 前端控制器
 *
 * @author chenxue
 * @since 2020-06-29
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysConfigService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public ServerResponse info(@PathVariable("id") Long id) {
        SysConfigEntity sysConfig = sysConfigService.getById(id);
        return ServerResponse.ok().put("sysConfig", sysConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public ServerResponse save(@RequestBody SysConfigEntity sysConfig) {
        ValidatorUtils.validateEntity(sysConfig, AddGroup.class);
        return sysConfigService.saveConfig(sysConfig);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public ServerResponse update(@RequestBody SysConfigEntity sysConfig) {
        ValidatorUtils.validateEntity(sysConfig, UpdateGroup.class);
        return sysConfigService.updateConfig(sysConfig);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public ServerResponse delete(@RequestBody List<Long> ids) {
        return sysConfigService.deleteConfig(ids);
    }

}
