package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.config.MyApplicationConfig;
import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.dao.SysMenuDao;
import com.zz.springbootproject.module.sys.dao.SysRoleDao;
import com.zz.springbootproject.module.sys.dao.SysUserTokenDao;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.module.sys.dao.SysUserDao;
import com.zz.springbootproject.module.sys.entity.SysUserTokenEntity;
import com.zz.springbootproject.module.sys.oauth2.TokenGenerator;
import com.zz.springbootproject.module.sys.service.SysUserRoleService;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.springbootproject.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 系统用户 服务实现类
 * @author chenxue
 * @since 2020-05-20
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private SysMenuDao sysMenuDao;

    @Resource
    private SysUserTokenDao sysUserTokenDao;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MyApplicationConfig config;

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<SysUserEntity> page = new Query<SysUserEntity>(params).getPage();
        List<SysUserEntity> list = baseMapper.queryPage(page,params);
        return new PageUtil(page.setRecords(list));
    }

    /**
     * @param userId
     * @return
     * @Description: 根据用户登录id, 查询用户权限
     * @Author: chenxue
     * @Date: 2020/5/20  12:57
     */
    @Override
    public List<String> queryPermById(Long userId) {
        Optional.ofNullable(userId).orElseThrow(() -> new ServerException("未查询到当前登录用户信息!"));
        //查询登录人角色
        List<SysRoleEntity> roleList = sysRoleDao.queryByUserId(userId);
        Optional.ofNullable(roleList).orElseThrow(() -> new ServerException("未查询到当前登录用户角色信息!"));
        List<Long> collect = roleList.stream().map(SysRoleEntity::getRoleId).distinct().collect(Collectors.toList());
        List<SysMenuEntity> menuList = null;
        //如果包含管理员角色，默认拥有所有权限
        if(collect.contains(Constant.RoleEnum.ADMIN.getValue())){
            menuList = sysMenuDao.queryByRoleId(null);
        }
        //根据角色，查询菜单权限
        menuList = sysMenuDao.queryByRoleId(collect);
        Optional.ofNullable(menuList).orElseThrow(() -> new ServerException("未查询到当前登录用户权限信息!"));
        List<String> perList = menuList.stream().map(SysMenuEntity::getPerms).distinct().collect(Collectors.toList());
        return perList;
    }

    /**
     * @param username
     * @Description: 根据用户名称，查询用户信息
     * @Author: chenxue
     * @Date: 2020/5/21  9:26
     */
    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    /**
     * @param userId
     * @Description: 生成token
     * @Author: chenxue
     * @Date: 2020/5/22  9:16
     */
    @Override
    public ServerResponse createToken(Long userId) {
        //生成token
        String token = TokenGenerator.generateValue();
        saveToken(o -> o.equals(config.getToken().getCacheType()),userId,token);
        return ServerResponse.ok().put("token",token);
    }


    /**
     * 根据配置决定token存储方式
     * @param predicate 比较函数
     * @param userId 用户id
     * @param token token
     */
    private void saveToken(Predicate<String> predicate,Long userId,String token) {
        if (predicate.test(MyApplicationConfig.CacheEnum.REDIS.getName())) {
            // 根据userId查询token,如果有值。则删除key为token的缓存
            Object oldToken = redisUtils.get(Objects.toString(userId));
            if(Objects.nonNull(oldToken)){
                redisUtils.del(Objects.toString(oldToken));
            }
            // redis 缓存存储 token
            redisUtils.set(token, userId, config.getToken().getExpireTime().getSeconds());
            redisUtils.set(Objects.toString(userId),token,config.getToken().getExpireTime().getSeconds());
        } else if (predicate.test(MyApplicationConfig.CacheEnum.DB.getName())) {
            //查询表中是否已经有用户token
            SysUserTokenEntity sysUserTokenEntity = sysUserTokenDao.selectById(userId);
            //为空则新建
            if (Objects.isNull(sysUserTokenEntity)) {
                SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
                tokenEntity.setUserId(userId);
                tokenEntity.setToken(token);
                tokenEntity.setExpireTime(Date.from(LocalDateTime.now().plusHours(Constant.EXPIRE).atZone(ZoneId.systemDefault()).toInstant()));
                sysUserTokenDao.insert(tokenEntity);
            } else {
                sysUserTokenEntity.setToken(token);
                sysUserTokenEntity.setExpireTime(Date.from(LocalDateTime.now().plusHours(Constant.EXPIRE).atZone(ZoneId.systemDefault()).toInstant()));
                sysUserTokenEntity.setUpdateTime(new Date());
                sysUserTokenDao.updateById(sysUserTokenEntity);
            }
        }
    }

    /**
     * @Description: 退出
     * @param
     * @Author: chenxue
     * @Date: 2020/5/23  17:59
     */
    @Override
    public ServerResponse lagout() {
        Long userId = ShiroUtils.getUser().getUserId();
        String token = TokenGenerator.generateValue();
        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        sysUserTokenDao.updateById(tokenEntity);
        return ServerResponse.ok();
    }

    /**
     * @Description: 新增用户
     * @param user
     * @Author: chenxue
     * @Date: 2020/6/1  16:16
     */
    @Override
    public void saveUser(SysUserEntity user) {
        // 密码加密
        user.setCreateUserId(ShiroUtils.getUser().getUserId());
        user.setCreateTime(new Date());
        //盐
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(),salt).toHex());
        user.setSalt(salt);
        this.save(user);
        // 保存用户角色关系
        sysUserRoleService.saveOrUpdateByParam(user.getUserId(),user.getRoleIdList());
    }
}
