package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysUserRoleEntity;
import com.zz.springbootproject.module.sys.dao.SysUserRoleDao;
import com.zz.springbootproject.module.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * 用户与角色对应关系 服务实现类
 * @author chenxue
 * @since 2020-05-23
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysUserRoleEntity> page = this.page(new Query<SysUserRoleEntity>(params).getPage(),new QueryWrapper<SysUserRoleEntity>());
      return new PageUtil(page);
   }
}
