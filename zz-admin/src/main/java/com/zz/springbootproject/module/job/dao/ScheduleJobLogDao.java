package com.zz.springbootproject.module.job.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志 Mapper 接口
 *
 * @author chenxue
 * @since 2020-06-18
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {

    List<ScheduleJobLogEntity> queryPage(IPage<ScheduleJobLogEntity> page, Map<String, Object> params);
}
