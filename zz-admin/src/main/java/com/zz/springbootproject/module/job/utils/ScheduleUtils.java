package com.zz.springbootproject.module.job.utils;

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
            // 构建Job
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJobEntity.getJobId())).build();
            // 放入参数
            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY,scheduleJobEntity);
            // 构建表达式调度器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJobEntity.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            // 表达式
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJobEntity.getJobId())).withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
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
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
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
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
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
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
