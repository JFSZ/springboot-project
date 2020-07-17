package com.zz.springbootproject.module.oss.service;

import com.zz.springbootproject.module.oss.entity.SysOssEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 *
 * @author chenxue
 * @since 2020-07-16
 */
public interface SysOssService extends IService<SysOssEntity> {
     PageUtil queryPage(Map<String, Object> params);
}
