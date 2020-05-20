package com.zz.springbootproject.module.sys.dao;

import com.zz.springbootproject.module.sys.entity.SysUserTokenEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token Mapper 接口
 * @author chenxue
 * @since 2020-05-20
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

}
