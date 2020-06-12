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

    /**
     * @Description: 根据角色 删除菜单、角色关系
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/9  17:14
     */
    void deleteRoleMenuByRoleId(@Param("list") List<String> list);

    /**
     * @Description:  根据菜单 删除菜单、角色关系
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/12  10:32
     */
    void deleteRoleMenuByMenuId(@Param("list") List<String> list);
}
