package com.zz.springbootproject.module.sys.dao;

import com.zz.springbootproject.module.sys.entity.SysRoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与菜单对应关系 Mapper 接口
 * @author chenxue
 * @since 2020-06-09
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {

    void deleteRoleMenu(@Param("list") List<String> list);
}
