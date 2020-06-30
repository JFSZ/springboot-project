package com.zz.springbootproject.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统配置信息表
 * @author chenxue
 * @since 2020-06-29
 */
@Data
@Accessors(chain = true)
@TableName("sys_config")
public class SysConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "参数ID不可为空!",groups = {UpdateGroup.class})
    private Long id;

    /**
     * key
     */
    @NotBlank(message = "参数名称不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String paramKey;

    /**
     * value
     */
    @NotBlank(message = "参数值不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String paramValue;

    /**
     * 状态   0：不启用   1：启用
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;


}
