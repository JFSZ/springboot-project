package com.zz.springbootproject.module.oss.service.impl;

import com.zz.springbootproject.module.oss.entity.SysOssEntity;
import com.zz.springbootproject.module.oss.dao.SysOssDao;
import com.zz.springbootproject.module.oss.service.SysOssService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;
import java.util.List;
import java.util.Map;

/**
 * 文件上传 服务实现类
 * @author chenxue
 * @since 2020-07-16
 */
@Service
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysOssEntity> page = new Query<SysOssEntity>(params).getPage();
      List<SysOssEntity> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }
}
