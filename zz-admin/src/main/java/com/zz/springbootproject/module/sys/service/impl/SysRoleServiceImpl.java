package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysRoleEntity;
import com.zz.springbootproject.module.sys.dao.SysRoleDao;
import com.zz.springbootproject.module.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 角色 服务实现类
 * @author chenxue
 * @since 2020-05-20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysRoleEntity> page = new Query<SysRoleEntity>(params).getPage();
      List<SysRoleEntity> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRoleEntity> queryByUserId(Long userId) {
        List<SysRoleEntity> sysRoleEntityList = baseMapper.queryByUserId(userId);
        return sysRoleEntityList;
    }
}
