package com.zz.springbootproject.module.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户 Mapper 接口
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    SysUserEntity queryByUserName(@Param("username") String username);

    List<SysUserEntity> queryPage(IPage<SysUserEntity> page, @Param("params") Map<String, Object> params);
}
