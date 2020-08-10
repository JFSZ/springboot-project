package com.zz.springbootproject.module.job.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * 定时任务
 *
 * @author chenxue
 * @since 2020-06-18
 */
@Data
@Accessors(chain = true)
@TableName("schedule_job")
public class ScheduleJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 任务id
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    /**
     * spring bean名称 即具体定时任务业务bean
     */
    @NotBlank(message = "任务名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String beanName;

    /**
     * 参数
     */
    private String params;

    /**
     * cron表达式
     */
    @NotBlank(message = "cron表达式不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
