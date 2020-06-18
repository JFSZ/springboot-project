package com.zz.springbootproject.module.job.service.impl;

import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.zz.springbootproject.module.job.dao.ScheduleJobDao;
import com.zz.springbootproject.module.job.service.ScheduleJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 定时任务 服务实现类
 * @author chenxue
 * @since 2020-06-18
 */
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<ScheduleJobEntity> page = new Query<ScheduleJobEntity>(params).getPage();
      List<ScheduleJobEntity> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }
}
