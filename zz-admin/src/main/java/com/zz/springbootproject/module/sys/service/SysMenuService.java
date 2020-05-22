package com.zz.springbootproject.module.sys.service;

import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
public interface SysMenuService extends IService<SysMenuEntity> {
 PageUtil queryPage(Map<String, Object> params);

 List<SysMenuEntity> queryByUserId(Long userId);
}
