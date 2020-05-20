package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysUserTokenEntity;
import com.zz.springbootproject.module.sys.dao.SysUserTokenDao;
import com.zz.springbootproject.module.sys.service.SysUserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * 系统用户Token 服务实现类
 * @author chenxue
 * @since 2020-05-20
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysUserTokenEntity> page = this.page(new Query<SysUserTokenEntity>(params).getPage(),new QueryWrapper<SysUserTokenEntity>());
      return new PageUtil(page);
   }
}
