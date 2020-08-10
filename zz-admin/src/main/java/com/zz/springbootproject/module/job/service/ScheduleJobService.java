package com.zz.springbootproject.module.job.service;

import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-06-18
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {
    PageUtil queryPage(Map<String, Object> params);

    /**
     * @param ids
     * @Description: 暂停定时任务
     * @Author: chenxue
     * @Date: 2020/6/20  10:18
     */
    void pauseJob(List<Long> ids);

    /**
     * @param ids
     * @Description: 恢复定时任务
     * @Author: chenxue
     * @Date: 2020/6/20  10:59
     */
    void resumeJob(List<Long> ids);

    /**
     * @param ids
     * @Description: 删除定时任务
     * @Author: chenxue
     * @Date: 2020/6/20  11:04
     */
    void deleteJob(List<Long> ids);

    ServerResponse saveJob(ScheduleJobEntity scheduleJob);

    /**
     * @param ids
     * @Description: 执行定时任务
     * @Author: chenxue
     * @Date: 2020/6/20  13:59
     */
    void runJob(List<Long> ids);

    /**
     * @param ids
     * @param status
     * @Description: 批量更新定时任务
     * @Author: chenxue
     * @Date: 2020/6/24  11:16
     */
    void updateBatchJob(List<Long> ids, String status);

    /**
     * @param scheduleJob
     * @Description: 修改定时任务
     * @Author: chenxue
     * @Date: 2020/6/20  13:59
     */
    ServerResponse updateJob(ScheduleJobEntity scheduleJob);
}
