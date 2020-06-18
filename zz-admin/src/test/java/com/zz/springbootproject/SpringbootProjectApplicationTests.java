package com.zz.springbootproject;

import com.zz.springbootproject.module.job.entity.ScheduleJobEntity;
import com.zz.springbootproject.module.job.utils.ScheduleUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootProjectApplicationTests {

    @Autowired
    private Scheduler scheduler;
    @Test
    public void test(){
        ScheduleJobEntity scheduleJobEntity = new ScheduleJobEntity();
        scheduleJobEntity.setBeanName("myTask");
        scheduleJobEntity.setJobId(123L);
        scheduleJobEntity.setParams("Tom");
        scheduleJobEntity.setCronExpression("0/10 * * * * ?");
        ScheduleUtils.startJob(scheduler,scheduleJobEntity);
        while (true){

        }
    }

}
