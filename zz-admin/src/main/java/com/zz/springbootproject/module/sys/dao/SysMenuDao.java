package com.zz.springbootproject.module.sys.dao;

import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理 Mapper 接口
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> queryByRoleId(List<Long> list);

    /**
     * @param
     * @Description: 查找不是按钮的菜单
     * @Author: chenxue
     * @Date: 2020/6/10  14:24
     */
    List<SysMenuEntity> queryNotButtonMenuList();
}
