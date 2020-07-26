package com.zz.springbootproject.module.oss.controller;

import java.util.Arrays;
import java.util.Map;

import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.module.oss.service.SysOssService;
import com.zz.springbootproject.module.oss.entity.SysOssEntity;
import com.zz.springbootproject.utils.PageUtil;
import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传 前端控制器
 * @author chenxue
 * @since 2020-07-16
 */
@RestController
@RequestMapping("/sys/oss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = sysOssService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:oss:all")
    public ServerResponse info(@PathVariable("id") Long id){
        SysOssEntity oss = sysOssService.getById(id);
        return ServerResponse.ok().put("oss", oss);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("oss:oss:all")
    public ServerResponse save(@RequestBody SysOssEntity oss){
    ValidatorUtils.validateEntity(oss, AddGroup.class);
        sysOssService.save(oss);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:oss:all")
    public ServerResponse update(@RequestBody SysOssEntity oss){
        ValidatorUtils.validateEntity(oss, UpdateGroup.class);
        sysOssService.updateById(oss);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public ServerResponse delete(@RequestBody Long[] ids){
        sysOssService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

    /**
     * @Description: 上传文件到本地
     * @param
     * @Author: chenxue
     * @Date: 2020/7/25  14:41
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public ServerResponse upload(@RequestParam String name,@RequestParam("file")MultipartFile file){
        sysOssService.upload(name,file);
        return ServerResponse.ok();
    }
}
