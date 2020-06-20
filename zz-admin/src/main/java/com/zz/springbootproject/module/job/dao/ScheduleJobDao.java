package com.zz.springbootproject.module.job.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 定时任务 Mapper 接口
 * @author chenxue
 * @since 2020-06-18
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {

    List<ScheduleJobEntity> queryPage(IPage<ScheduleJobEntity> page, @Param("params") Map<String, Object> params);

    void updateBatchJob(List<Long> list, @Param("status") String status);
}
