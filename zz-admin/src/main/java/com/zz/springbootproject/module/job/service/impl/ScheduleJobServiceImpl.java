package com.zz.springbootproject.module.job.service.impl;

import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.zz.springbootproject.module.job.dao.ScheduleJobDao;
import com.zz.springbootproject.module.job.service.ScheduleJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.springbootproject.module.job.utils.ScheduleUtils;
import com.zz.springbootproject.utils.ServerResponse;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时任务 服务实现类
 * @author chenxue
 * @since 2020-06-18
 */
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobEntity> implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<ScheduleJobEntity> page = new Query<ScheduleJobEntity>(params).getPage();
      List<ScheduleJobEntity> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }

    /**
     * @Description: 暂停定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  10:18
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseJob(List<Long> ids) {
        for (Long id : ids){
            ScheduleUtils.pauseJob(scheduler,id);
        }
        updateBatchJob(ids, Constant.ONE);
    }

    /**
     * @Description: 恢复定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  10:59
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resumeJob(List<Long> ids) {
        for (Long id : ids){
            ScheduleUtils.resumeJob(scheduler,id);
        }
        updateBatchJob(ids, Constant.ZERO);
    }

    /**
     * @Description: 删除定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  11:04
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(List<Long> ids) {
        for (Long id : ids){
            ScheduleUtils.deleteJob(scheduler,id);
        }
        this.removeByIds(ids);
    }

    /**
     * @Description: 保存定时任务
     * @param scheduleJob
     * @Author: chenxue
     * @Date: 2020/6/20  11:22
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse saveJob(ScheduleJobEntity scheduleJob) {
        scheduleJob.setStatus(1);
        scheduleJob.setCreateTime(new Date());
        this.saveOrUpdate(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler,scheduleJob);
        return ServerResponse.ok();
    }

    /**
     * @Description: 更新定时任务
     * @param scheduleJob
     * @Author: chenxue
     * @Date: 2020/6/20  11:22
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse updateJob(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateJob(scheduler,scheduleJob);
        this.updateById(scheduleJob);
        return ServerResponse.ok();
    }


    /**
     * @Description: 执行定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  13:59
     */
    @Override
    public void runJob(List<Long> ids) {
        for (Long id : ids){
            ScheduleUtils.runJob(scheduler,this.getById(id));
        }
    }

    @Override
    public void updateBatchJob(List<Long> ids, String status) {
        baseMapper.updateBatchJob(ids,status);
    }
}
