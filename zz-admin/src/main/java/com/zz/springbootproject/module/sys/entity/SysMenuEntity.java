package com.zz.springbootproject.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.zz.springbootproject.validator.group.AddGroup;
import com.zz.springbootproject.validator.group.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Data
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    @NotNull(message = "菜单id不可为空",groups = {UpdateGroup.class})
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 父菜单名称
     */
    @TableField(exist=false)
    private String parentName;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不可为空",groups = {AddGroup.class})
    private String name;

    /**
     * 菜单URL
     */
    @NotBlank(message = "菜单URL不可为空",groups = {AddGroup.class})
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     * 需要在 type=2 按钮时才有校验。可以自定义校验规则
     */
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    @NotNull(message = "菜单类型不可为空",groups = {AddGroup.class})
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;

    @TableField(exist=false)
    private List<?> list;
}
