package com.zz.springbootproject.module.job.service;

import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-06-18
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {
     PageUtil queryPage(Map<String, Object> params);
}
