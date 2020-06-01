package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.sys.entity.SysUserRoleEntity;
import com.zz.springbootproject.module.sys.dao.SysUserRoleDao;
import com.zz.springbootproject.module.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import java.util.*;

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

   /**
    * @Description: 保存/更新用户角色
    * @param userId 用户id
    * @param roleIdList 角色list
    * @Author: chenxue
    * @Date: 2020/6/1  16:34
    */
    @Override
    public void saveOrUpdateByParam(Long userId, List<Long> roleIdList) {
        Optional.ofNullable(roleIdList).orElseThrow(() -> new ServerException("用户角色不可为空!"));
        //先删除原有数据
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        this.removeByMap(map);
        List<SysUserRoleEntity> list = new ArrayList<>();
        roleIdList.stream().filter(Objects::nonNull).forEach(o -> {
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(o);
            list.add(sysUserRoleEntity);
        });
        if(list.size() > 0){
            this.saveBatch(list);
        }
    }
}
