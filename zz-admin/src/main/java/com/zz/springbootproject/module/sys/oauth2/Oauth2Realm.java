package com.zz.springbootproject.module.sys.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.config.TokenConfig;
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

    @Autowired
    private TokenConfig config;

    /**
     * @param token
     * @Description: 如果自定义了Token生成规则，则需要重写该方法.否者会报错:does not support authentication token
     * @Author: chenxue
     * @Date: 2020/5/23  17:28
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    /**
     * @param principalCollection
     * @Description: 授权(验证权限时调用)
     * @Author: chenxue
     * @Date: 2020/5/14  17:09
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUserEntity userEntity = (SysUserEntity) principalCollection.getPrimaryPrincipal();
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
     * @param authenticationToken
     * @Description: 身份认证(登录时调用)
     * @Author: chenxue
     * @Date: 2020/5/14  17:09
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = Objects.toString(authenticationToken.getPrincipal());
        String userId = "";
        // 根据配置决定从哪里读取token
        if (TokenConfig.CacheEnum.REDIS.getName().equalsIgnoreCase(config.getCacheType())) {
            Object userIdCache = redisUtils.get(token);
            Optional.ofNullable(userIdCache).orElseThrow(() -> new IncorrectCredentialsException("token失效，请重新登录"));
            userId = Objects.toString(userIdCache);
        } else if (TokenConfig.CacheEnum.DB.getName().equalsIgnoreCase(config.getCacheType())) {
            //根据token查询，token是否失效
            QueryWrapper<SysUserTokenEntity> wrapper = new QueryWrapper<>();
            wrapper.and(i -> i.eq("token", token));
            SysUserTokenEntity sysUserTokenEntity = sysUserTokenService.getOne(wrapper);
            Optional.ofNullable(sysUserTokenEntity)
                    .map(o -> o.getExpireTime().getTime() < TimeUnit.NANOSECONDS.toMillis(System.nanoTime()))
                    .orElseThrow(() -> new IncorrectCredentialsException("token失效，请重新登录"));
            userId = Objects.toString(sysUserTokenEntity.getUserId());
        }

        //查询用户信息
        SysUserEntity sysUserEntity = sysUserService.getById(userId);
        //如果用户被禁用
        Optional.ofNullable(sysUserEntity)
                .map(o -> Constant.ONE.equals(o.getStatus()))
                .orElseThrow(() -> new LockedAccountException("账号已被锁定,请联系管理员"));

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUserEntity, token, getName());
        return info;
    }

}
