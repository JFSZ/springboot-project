package com.zz.springbootproject.module.oss.dao;

import com.zz.springbootproject.module.oss.entity.SysOssEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 文件上传 Mapper 接口
 *
 * @author chenxue
 * @since 2020-07-16
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
    List<SysOssEntity> queryPage(IPage<SysOssEntity> page, Map<String, Object> params);
}
