package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysUserService extends IService<SysUserEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @Description: 根据用户登录id,查询用户权限
     * @param userId
     * @Author: chenxue
     * @Date: 2020/5/20  12:56
     * @return
     */
    List<String> queryPermById(Long userId);
}
