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
        return ServerResponse.ok().put("schedule_job", scheduleJob);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("job:schedule:save")
    public ServerResponse save(@RequestBody ScheduleJobEntity scheduleJob){
        scheduleJobService.save(scheduleJob);
        return ServerResponse.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("job:schedule:update")
    public ServerResponse update(@RequestBody ScheduleJobEntity scheduleJob){
        scheduleJobService.updateById(scheduleJob);
        return ServerResponse.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("job:schedule:delete")
    public ServerResponse delete(@RequestBody Long[] ids){
        scheduleJobService.removeByIds(Arrays.asList(ids));
        return ServerResponse.ok();
    }

}
