package com.zz.springbootproject.utils;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-05-22 17:41
 **/
public class ShiroUtils {
    public static SysUserEntity getUser(){
        SysUserEntity sysUserEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        return sysUserEntity;
    }
}
