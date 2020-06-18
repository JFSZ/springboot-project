package com.zz.springbootproject.module.job.dao;

import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志 Mapper 接口
 * @author chenxue
 * @since 2020-06-18
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {

}
