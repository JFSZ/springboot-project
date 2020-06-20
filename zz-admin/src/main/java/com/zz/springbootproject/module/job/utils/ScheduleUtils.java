package com.zz.springbootproject.module.job.utils;

import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.job.config.ScheduleJob;
import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @description: quartz 工具类
 * @author: chenxue
 * @create: 2020-06-18 10:27
 **/
@Slf4j
public class ScheduleUtils {
    private static final String JOB_KEY = "Task_";
    /**
     * @Description: 获取jobKey
     * @param jobId
     * @Author: chenxue
     * @Date: 2020/6/18  14:40
     */
    public static JobKey getJobKey(Long jobId){
        return JobKey.jobKey(JOB_KEY + jobId);
    }

    /**
     * @Description: 获取tiggerKey
     * @param jobId
     * @Author: chenxue
     * @Date: 2020/6/18  14:40
     */
    public static TriggerKey getTriggerKey(Long jobId){
        return TriggerKey.triggerKey(JOB_KEY + jobId);
    }

    public static CronTrigger getCronTrigger(Scheduler scheduler,Long jobId){
        try {
            Trigger trigger = scheduler.getTrigger(getTriggerKey(jobId));
            if(trigger instanceof CronTrigger){
                return (CronTrigger)trigger;
            }
            return null;
        } catch (SchedulerException e) {
            log.error("获取定时任务CronTrigger出现异常", e);
            throw new ServerException("获取定时任务CronTrigger出现异常");
        }
    }

    /**
     * @Description:   新增定时任务
     * @param scheduler 任务调度器
     * @param scheduleJobEntity 定时任务配置类
     * @Author: chenxue
     * @Date: 2020/6/18  14:12
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJobEntity){
        try {
            log.info("新增定时任务,任务id:" + scheduleJobEntity.getJobId());
            // 构建Job
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJobEntity.getJobId())).build();
            // 放入参数
            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY,scheduleJobEntity);
            // 构建表达式调度器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            // 表达式
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJobEntity.getJobId())).withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,trigger);
            if(scheduleJobEntity.getStatus().equals(Constant.ONE)){
                pauseJob(scheduler,scheduleJobEntity.getJobId());
            }
        } catch (SchedulerException e) {
            log.error("新增定时任务失败!",e);
            throw new ServerException("新增定时任务失败!",e);
        }
    }

    /**
     * @Description: 更新任务
     * @param scheduler
     * @param scheduleJobEntity
     * @Author: chenxue
     * @Date: 2020/6/18  15:16
     */
    public static void updateJob(Scheduler scheduler, ScheduleJobEntity scheduleJobEntity){
        try {
            log.info("更新定时任务,任务id:" + scheduleJobEntity.getJobId());
            TriggerKey triggerKey = getTriggerKey(scheduleJobEntity.getJobId());
            CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(scheduleJobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            CronTrigger trigger = getCronTrigger(scheduler,scheduleJobEntity.getJobId());
            // 重新构建新的表达式Trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(builder).build();
            trigger.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY,scheduleJobEntity);
            scheduler.rescheduleJob(triggerKey,trigger);
            if(scheduleJobEntity.getStatus().equals(Constant.ONE)){
                pauseJob(scheduler,scheduleJobEntity.getJobId());
            }
        } catch (SchedulerException e) {
            log.error("更新定时任务失败!",e);
            throw new ServerException("更新定时任务失败!",e);
        }
    }

    /**
     * @Description: 执行定时任务
     * @param scheduler
     * @param scheduleJob
     * @Author: chenxue
     * @Date: 2020/6/18  14:47
     */
    public static void runJob(Scheduler scheduler, ScheduleJobEntity scheduleJob){
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(ScheduleJobEntity.JOB_PARAM_KEY,scheduleJob);
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()),jobDataMap);
        }catch (Exception e){
            log.error("开始执行任务:" + getJobKey((scheduleJob.getJobId())) + "失败，失败原因:" + e);
            throw new ServerException("执行定时任务失败!",e);
        }
    }


    /**
     * @Description: 暂停任务
     * @param scheduler
     * @param jobId
     * @Author: chenxue
     * @Date: 2020/6/18  15:27
     */
    public static void pauseJob(Scheduler scheduler, Long jobId){
        try {
            log.info("暂停定时任务，任务id : " + jobId);
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("暂停任务失败，任务id : " + jobId);
            throw new ServerException("暂停任务失败,",e);
        }
    }

    /**
     * @Description: 恢复定时任务
     * @param scheduler
     * @param jobId
     * @Author: chenxue
     * @Date: 2020/6/18  15:29
     */
    public static void resumeJob(Scheduler scheduler, Long jobId){
        try {
            log.info("恢复定时任务，任务id : " + jobId);
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("恢复任务失败，任务id : " + jobId);
            throw new ServerException("恢复任务失败,",e);
        }
    }


    /**
     * @Description: 删除定时任务
     * @param scheduler
     * @param jobId
     * @Author: chenxue
     * @Date: 2020/6/18  15:29
     */
    public static void deleteJob(Scheduler scheduler, Long jobId){
        try {
            log.info("删除定时任务，任务id : " + jobId);
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除任务失败，任务id : " + jobId);
            throw new ServerException("删除任务失败,",e);
        }
    }
}
