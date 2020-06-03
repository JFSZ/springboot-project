package com.zz.springbootproject.module.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    List<SysRoleEntity> queryByUserId(@Param("userId") Long userId);

    List<SysRoleEntity> queryPage(IPage<SysRoleEntity> page, @Param("params") Map<String, Object> params);
}
