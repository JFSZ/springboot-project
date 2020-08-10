package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 系统用户 服务类
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysUserService extends IService<SysUserEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @param userId
     * @return
     * @Description: 根据用户登录id, 查询用户权限
     * @Author: chenxue
     * @Date: 2020/5/20  12:56
     */
    List<String> queryPermById(Long userId);

    /**
     * @param username
     * @Description: 根据用户名称，查询用户信息
     * @Author: chenxue
     * @Date: 2020/5/21  9:26
     */
    SysUserEntity queryByUserName(String username);


    /**
     * @param userId
     * @Description: 生成token
     * @Author: chenxue
     * @Date: 2020/5/22  9:16
     */
    ServerResponse createToken(Long userId);

    /**
     * @param
     * @Description: 退出
     * @Author: chenxue
     * @Date: 2020/5/23  17:59
     */
    ServerResponse lagout();

    /**
     * @param user
     * @Description: 新增用户
     * @Author: chenxue
     * @Date: 2020/6/1  16:16
     */
    void saveUser(SysUserEntity user);
}
