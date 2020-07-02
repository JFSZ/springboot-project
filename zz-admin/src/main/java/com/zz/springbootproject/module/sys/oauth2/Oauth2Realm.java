package com.zz.springbootproject.module.sys.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.module.sys.entity.SysUserTokenEntity;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.zz.springbootproject.module.sys.service.SysUserTokenService;
import com.zz.springbootproject.utils.RedisUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 自定义认证实现类
 * @author: chenxue
 * @create: 2020-05-14 17:08
 **/
@Component
public class Oauth2Realm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @Description: 如果自定义了Token生成规则，则需要重写该方法.否者会报错:does not support authentication token
     * @param token
     * @Author: chenxue 
     * @Date: 2020/5/23  17:28
     */ 
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    /**
     * @Description: 授权(验证权限时调用)
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
        List<String> permList = sysUserService.queryPermById(userId);
        Set<String> permSet = new HashSet<>();
        permList.stream().filter(Objects::nonNull).forEach(o -> permSet.addAll(Arrays.asList(o.trim().split(","))));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permSet);
        return info;
    }

    /**
     * @Description:  身份认证(登录时调用)
     * @param authenticationToken
     * @Author: chenxue 
     * @Date: 2020/5/14  17:09
     */ 
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = Objects.toString(authenticationToken.getPrincipal());
        Object tokenCache = redisUtils.get(token);
        Optional.ofNullable(tokenCache).orElseThrow(() -> new IncorrectCredentialsException("token失效，请重新登录"));
        String userId = Objects.toString(tokenCache);
        //查询用户信息
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        //如果用户被禁用
        Optional.ofNullable(sysUserEntity)
                .map(o -> Constant.ONE.equals(o.getStatus()))
                .orElseThrow(() -> new LockedAccountException("账号已被锁定,请联系管理员"));

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUserEntity,token,getName());
        return info;
    }

}
