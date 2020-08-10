package com.zz.springbootproject.module.sys.service.impl;

import com.zz.springbootproject.common.Constant;
import com.zz.springbootproject.module.sys.dao.SysUserRoleDao;
import com.zz.springbootproject.module.sys.entity.SysMenuEntity;
import com.zz.springbootproject.module.sys.dao.SysMenuDao;
import com.zz.springbootproject.module.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.springbootproject.util.ServerResponse;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zz.springbootproject.utils.PageUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单管理 服务实现类
 *
 * @author chenxue
 * @since 2020-05-20
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(new Query<SysMenuEntity>(params).getPage(), new QueryWrapper<SysMenuEntity>());
        List<SysMenuEntity> records = page.getRecords();
        Optional.ofNullable(records)
                .orElse(new ArrayList<>())
                .stream()
                .filter(Objects::nonNull)
                .forEach(o -> {
                    SysMenuEntity sysMenuEntity = this.getById(o.getParentId());
                    if (Objects.nonNull(sysMenuEntity)) {
                        o.setParentName(sysMenuEntity.getName());
                    }
                });
        return new PageUtil(page);
    }

    /**
     * @param userId
     * @Description: 查询登录人拥有的菜单
     * @Author: chenxue
     * @Date: 2020/5/22  18:21
     */
    @Override
    public List<SysMenuEntity> queryByUserId(Long userId) {
        List<SysMenuEntity> menuList;
        //查询登录人拥有的角色
        List<Long> roleList = sysUserRoleDao.queryByUserId(userId);
        //如果登录人含有管理员角色，返回所有菜单
        if (roleList.contains(Constant.RoleEnum.ADMIN.getValue())) {
            menuList = baseMapper.queryByRoleId(null);
        } else {
            menuList = baseMapper.queryByRoleId(roleList);
        }
        List<SysMenuEntity> collect = menuList.stream().filter(o -> Long.valueOf(Constant.ZERO) == o.getParentId()).collect(Collectors.toList());
        return getMenuTreeList(collect, menuList);
    }

    /**
     * @param roleId 角色id
     * @Description: 根据角色查询菜单权限
     * @Author: chenxue
     * @Date: 2020/6/5  17:12
     */
    @Override
    public List<SysMenuEntity> queryByRoleId(Long roleId) {
        List<SysMenuEntity> menuEntityList = baseMapper.queryByRoleId(roleId == null ? null : Arrays.asList(roleId));
       /* List<SysMenuEntity> collect = Optional.ofNullable(menuEntityList).orElse(new ArrayList<>()).stream()
                .filter(o -> Long.valueOf(Constant.ZERO) == o.getParentId()).collect(Collectors.toList());
        List<SysMenuEntity> menuTreeList = getMenuTreeList(collect, menuEntityList);*/
        return menuEntityList;
    }

    /**
     * @param
     * @Description: 查找不是按钮的菜单
     * @Author: chenxue
     * @Date: 2020/6/10  14:24
     */
    @Override
    public List<SysMenuEntity> queryNotButtonMenuList() {
        return baseMapper.queryNotButtonMenuList();
    }

    /**
     * @param sysMenu
     * @Description: 保存、更新菜单
     * @Author: chenxue
     * @Date: 2020/6/12  10:13
     */
    @Override
    public ServerResponse saveOrUpdateMenu(SysMenuEntity sysMenu) {
        this.saveOrUpdate(sysMenu);
        return ServerResponse.ok();
    }

    /**
     * @param collect  返回数据
     * @param menuList 总数据
     * @Description:
     * @Author: chenxue
     * @Date: 2020/5/23  15:24
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> collect, List<SysMenuEntity> menuList) {
        for (SysMenuEntity sysMenuEntity : collect) {
            List<SysMenuEntity> list = new ArrayList<>();
            if (Constant.MenuEnum.CATALOG.getValue() == sysMenuEntity.getType()) {
                getMenuTreeList(list, menuList);
            }
            for (SysMenuEntity entity : menuList) {
                if (entity.getParentId() == sysMenuEntity.getMenuId()) {
                    list.add(entity);
                }
            }
            sysMenuEntity.setList(list);
        }
        return collect;
    }
}
