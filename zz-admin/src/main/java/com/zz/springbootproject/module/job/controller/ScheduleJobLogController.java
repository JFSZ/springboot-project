package com.zz.springbootproject.module.job.controller;

import java.util.Arrays;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.utils.ServerResponse;
import com.zz.springbootproject.module.job.service.ScheduleJobLogService;
import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务日志 前端控制器
 * @author chenxue
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/job/schedule-job-log-entity")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("job:scheduleJobLogEntity:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = scheduleJobLogService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("job:scheduleJobLogEntity:info")
    public ServerResponse info(@PathVariable("id") Long id){
        ScheduleJobLogEntity schedule_job_log = scheduleJobLogService.getById(id);
        return ServerResponse.ok().put("schedule_job_log", schedule_job_log);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("job:scheduleJobLogEntity:save")
    public ServerResponse save(@RequestBody ScheduleJobLogEntity schedule_job_log){
        scheduleJobLogService.save(schedule_job_log);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("job:scheduleJobLogEntity:update")
    public ServerResponse update(@RequestBody ScheduleJobLogEntity schedule_job_log){
        scheduleJobLogService.updateById(schedule_job_log);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("job:scheduleJobLogEntity:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        scheduleJobLogService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
