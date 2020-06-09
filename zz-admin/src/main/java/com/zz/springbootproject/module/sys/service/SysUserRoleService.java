package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysUserRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-23
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {
     PageUtil queryPage(Map<String, Object> params);


    /**
     * @Description: 保存/更新用户角色
     * @param userId 用户id
     * @param roleIdList 角色list
     * @Author: chenxue
     * @Date: 2020/6/1  16:34
     */
    void saveOrUpdateByParam(Long userId, List<Long> roleIdList);

    /**
     * @Description: 删除用户、角色关系
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/9  17:14
     */
    void deleteByRoleId(List<String> ids);

    /**
     * @Description: 删除角色、用户表数据
     * @param userIds
     * @Author: chenxue
     * @Date: 2020/6/9  17:20
     */
    void deleteByUserId(List<Long> userIds);
}
