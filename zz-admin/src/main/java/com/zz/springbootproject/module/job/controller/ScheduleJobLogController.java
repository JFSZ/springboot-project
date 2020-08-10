package com.zz.springbootproject.module.job.controller;

import java.util.Map;

import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.zz.springbootproject.util.ServerResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.zz.springbootproject.module.job.service.ScheduleJobLogService;
import com.zz.springbootproject.utils.PageUtil;

import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务日志 前端控制器
 *
 * @author chenxue
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/job/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("job:schedule:list")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        PageUtil page = scheduleJobLogService.queryPage(params);
        return page;
    }

    /**
     * @param id
     * @Description: 查询日志信息
     * @Author: chenxue
     * @Date: 2020/6/22  11:44
     */
    @RequestMapping("/info/{id}")
    public ServerResponse info(@PathVariable("id") Long id) {
        ScheduleJobLogEntity job = scheduleJobLogService.getById(id);
        return ServerResponse.ok().put("job", job);
    }

}
