package com.zz.springbootproject.module.job.service.impl;

import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.zz.springbootproject.module.job.dao.ScheduleJobLogDao;
import com.zz.springbootproject.module.job.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * 定时任务日志 服务实现类
 * @author chenxue
 * @since 2020-06-18
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<ScheduleJobLogEntity> page = this.page(new Query<ScheduleJobLogEntity>(params).getPage(),new QueryWrapper<ScheduleJobLogEntity>());
      return new PageUtil(page);
   }
}
