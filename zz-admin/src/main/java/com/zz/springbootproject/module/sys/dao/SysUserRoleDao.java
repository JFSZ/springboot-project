package com.zz.springbootproject.module.sys.dao;

import com.zz.springbootproject.module.sys.entity.SysUserRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色对应关系 Mapper 接口
 * @author chenxue
 * @since 2020-05-23
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

    List<Long> queryByUserId(@Param("userId") Long userId);
    /**
     * @Description: 删除用户、角色关系
     * @param list
     * @Author: chenxue
     * @Date: 2020/6/9  17:14
     */
    void deleteByRoleId(List<String> list);

    /**
     * @Description: 删除角色、用户表数据
     * @param userIds
     * @Author: chenxue
     * @Date: 2020/6/9  17:20
     */
    void deleteByUserId(List<Long> userIds);
}
