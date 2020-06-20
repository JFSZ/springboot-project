package com.zz.springbootproject.module.job.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.utils.ServerResponse;
import com.zz.springbootproject.module.job.service.ScheduleJobService;
import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务 前端控制器
 * @author chenxue
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/job/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("job:schedule:list")
    public PageUtil list(@RequestParam Map<String, Object> params){
        PageUtil page = scheduleJobService.queryPage(params);
        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("job:schedule:info")
    public ServerResponse info(@PathVariable("id") Long id){
        ScheduleJobEntity scheduleJob = scheduleJobService.getById(id);
        return ServerResponse.ok().put("job", scheduleJob);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("job:schedule:save")
    public ServerResponse save(@RequestBody ScheduleJobEntity scheduleJob){
        return scheduleJobService.saveJob(scheduleJob);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("job:schedule:update")
    public ServerResponse update(@RequestBody ScheduleJobEntity scheduleJob){
        return scheduleJobService.updateJob(scheduleJob);
    }

    /**
     * 删除定时任务
     */
    @RequestMapping("/delete")
    @RequiresPermissions("job:schedule:delete")
    public ServerResponse delete(@RequestBody List<Long> ids){
        scheduleJobService.deleteJob(ids);
        return ServerResponse.ok();
    }

    /**
     * @Description: 暂停定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  10:18
     */
    @RequestMapping("/pauseJob")
    @RequiresPermissions("job:schedule:pause")
    public ServerResponse pauseJob(@RequestBody List<Long> ids){
        scheduleJobService.pauseJob(ids);
        return ServerResponse.ok();
    }

    /**
     * @Description: 恢复定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  10:59
     */
    @RequestMapping("/resumeJob")
    @RequiresPermissions("job:schedule:resume")
    public ServerResponse resumeJob(@RequestBody List<Long> ids){
        scheduleJobService.resumeJob(ids);
        return ServerResponse.ok();
    }

    /**
     * @Description: 执行定时任务
     * @param ids
     * @Author: chenxue
     * @Date: 2020/6/20  13:59
     */
    @RequestMapping("/runJob")
    @RequiresPermissions("job:schedule:run")
    public ServerResponse runJob(@RequestBody List<Long> ids){
        scheduleJobService.runJob(ids);
        return ServerResponse.ok();
    }
}
