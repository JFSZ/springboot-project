package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysConfigEntity;
import com.zz.springbootproject.module.sys.dao.SysConfigDao;
import com.zz.springbootproject.module.sys.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;
import java.util.List;
import java.util.Map;

/**
 * 系统配置信息表 服务实现类
 * @author chenxue
 * @since 2020-06-29
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysConfigEntity> page = new Query<SysConfigEntity>(params).getPage();
      List<SysConfigEntity> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }
}
