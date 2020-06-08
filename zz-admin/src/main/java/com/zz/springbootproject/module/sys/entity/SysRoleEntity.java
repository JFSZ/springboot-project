package com.zz.springbootproject.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * 角色
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @NotBlank(message = "ID不可为空!",groups = UpdateGroup.class)
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不可为空!",groups = AddGroup.class)
    private String roleName;

    /**
     * "角色描述
     */
    @NotBlank(message = "角色描述不可为空!",groups = AddGroup.class)
    private String remark;

    /**
     * 创建者ID
     */
    private Long createUserId;

    /**
     * 创建时间
     * DateTimeFormat : 入参为String，可以解析为date
     * JsonFormat: 格式化数据库查询数据
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 菜单List
     */
    @TableField(exist = false)
    public List<Long> menuIdList;


}
