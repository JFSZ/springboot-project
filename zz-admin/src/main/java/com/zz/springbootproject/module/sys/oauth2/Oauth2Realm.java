package com.zz.springbootproject.module.sys.oauth2;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 自定义认证实现类
 * @author: chenxue
 * @create: 2020-05-14 17:08
 **/
@Component
public class Oauth2Realm extends AuthorizingRealm {
    /**
     * @Description: 权限认证
     * @param principalCollection
     * @Author: chenxue 
     * @Date: 2020/5/14  17:09
     */ 
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUserEntity userEntity = (SysUserEntity)principalCollection.getPrimaryPrincipal();
        // 查询当前登陆用户信息
        Long userId = userEntity.getUserId();
        //根据用户id，查询用户权限

        Set<String> permSet = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permSet);
        return info;
    }

    /**
     * @Description:  身份认证
     * @param authenticationToken
     * @Author: chenxue 
     * @Date: 2020/5/14  17:09
     */ 
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        return info;
    }
}
