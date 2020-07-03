package com.zz.springbootproject.utils;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;

import java.util.Optional;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-05-22 17:41
 **/
public class ShiroUtils {
    public static SysUserEntity getUser(){
        SysUserEntity principal = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        Optional.ofNullable(principal).orElseThrow(() -> new IncorrectCredentialsException("token失效，请重新登录"));
        return principal;
    }
}
