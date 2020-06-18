package com.zz.springbootproject.module.job.config;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.zz.springbootproject.module.job.service.ScheduleJobLogService;
import com.zz.springbootproject.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 定时任务Job
 * @Author: chenxue
 * @Date: 2020/6/18  10:22
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");
        // 获取JobDataMap 中的参数
        ScheduleJobEntity scheduleJobEntity = (ScheduleJobEntity) jobExecutionContext.getMergedJobDataMap().get(ScheduleJobEntity.JOB_PARAM_KEY);
        //保存 定时任务日志
        ScheduleJobLogEntity scheduleJobLogEntity = new ScheduleJobLogEntity();
        scheduleJobLogEntity.setBeanName(scheduleJobEntity.getBeanName());
        scheduleJobLogEntity.setCreateTime(new Date());
        scheduleJobLogEntity.setJobId(scheduleJobEntity.getJobId());
        scheduleJobLogEntity.setParams(scheduleJobEntity.getParams());
        // 开始时间
        Long startTime = System.nanoTime();
        try {
            // 反射，执行目标定时任务
            log.info("定时任务【" + scheduleJobEntity.getBeanName() + "】开始执行，任务ID为：" + scheduleJobEntity.getJobId());
            Object target = SpringContextUtils.getBean(scheduleJobEntity.getBeanName());
            if(Objects.isNull(target)){
                log.info("未找到名称为:" + scheduleJobEntity.getBeanName() + "的bean.");
                throw new ServerException("未找到名称为:" + scheduleJobEntity.getBeanName() + "的bean.");
            }
            Method method = target.getClass().getDeclaredMethod("execute", String.class);
            // 执行目标方法
            method.invoke(target, scheduleJobEntity.getParams());
            scheduleJobLogEntity.setStatus(0);
            Integer totalTime = (int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            scheduleJobLogEntity.setTimes(totalTime);
            log.info("任务执行完毕,任务ID为: " + scheduleJobEntity.getJobId() + ",任务总耗时:" + totalTime + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJobEntity.getJobId(), e);
            Integer totalTime = (int) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            scheduleJobLogEntity.setError(e.getMessage());
            scheduleJobLogEntity.setTimes(totalTime);
            scheduleJobLogEntity.setStatus(1);
        } finally {
            scheduleJobLogService.save(scheduleJobLogEntity);
        }

    }
}
