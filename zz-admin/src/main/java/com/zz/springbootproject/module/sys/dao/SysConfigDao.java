package com.zz.springbootproject.module.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 * 系统配置信息表 Mapper 接口
 * @author chenxue
 * @since 2020-06-29
 */
@Mapper
public interface SysConfigDao extends BaseMapper<SysConfigEntity> {
    List<SysConfigEntity> queryPage(IPage<SysConfigEntity> page, Map<String, Object> params);
}
