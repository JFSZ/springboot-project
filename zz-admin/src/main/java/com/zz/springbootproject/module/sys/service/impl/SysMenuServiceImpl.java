package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.zz.springbootproject.module.sys.dao.SysMenuDao;
import com.zz.springbootproject.module.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<SysMenuEntity> page = this.page(new Query<SysMenuEntity>(params).getPage(),new QueryWrapper<SysMenuEntity>());
      return new PageUtil(page);
   }

   /**
    * @Description: 查询登录人拥有的菜单
    * @param userId
    * @Author: chenxue
    * @Date: 2020/5/22  18:21
    */
    @Override
    public List<SysMenuEntity> queryByUserId(Long userId) {

        return null;
    }
}
