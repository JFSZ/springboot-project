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
}
