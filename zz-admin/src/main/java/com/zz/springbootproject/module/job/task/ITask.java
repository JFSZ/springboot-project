package com.zz.springbootproject.module.job.task;

/**
 * @description: 定时任务接口
 * @author: chenxue
 * @create: 2020-06-18 10:23
 **/
public interface ITask {
    void execute(String params);
}
