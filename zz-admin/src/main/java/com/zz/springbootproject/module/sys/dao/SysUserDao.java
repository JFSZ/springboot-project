package com.zz.springbootproject.module.sys.dao;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

}
