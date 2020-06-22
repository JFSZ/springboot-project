package com.zz.springbootproject.module.job.service.impl;

import com.zz.springbootproject.module.job.entity.ScheduleJobLogEntity;
import com.zz.springbootproject.module.job.dao.ScheduleJobLogDao;
import com.zz.springbootproject.module.job.service.ScheduleJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 定时任务日志 服务实现类
 *
 * @author chenxue
 * @since 2020-06-18
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<ScheduleJobLogEntity> page = new Query<ScheduleJobLogEntity>(params).getPage();
        if(params.containsKey("selectDate")){
            //日期处理
            String selectDate = Objects.toString(params.get("selectDate"));
            String[] dateArr = selectDate.split(",");
            if(Objects.nonNull(dateArr) && dateArr.length > 0){
                String startDate = dateArr[0] + " 00:00:00";
                String endDate = dateArr[1] + " 23:59:59";
                params.put("startDate",startDate);
                params.put("endDate",endDate);
            }
        }

        List<ScheduleJobLogEntity> list = baseMapper.queryPage(page, params);
        return new PageUtil(page.setRecords(list));
    }
}
