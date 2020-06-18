package com.zz.springbootproject.module.job.task;

import org.springframework.stereotype.Component;

/**
 * @description: 具体定时任务
 * @author: chenxue
 * @create: 2020-06-18 10:23
 **/
@Component("myTask")
public class MyTask implements ITask{
    @Override
    public void execute(String params) {
        System.out.println("MyTask 任务开始执行... 参数为" + params);
    }
}
