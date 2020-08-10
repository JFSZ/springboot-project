package com.zz.springbootproject.module.oss.service.impl;

import com.zz.springbootproject.config.FileConfig;
import com.zz.springbootproject.exception.ServerException;
import com.zz.springbootproject.module.oss.entity.SysOssEntity;
import com.zz.springbootproject.module.oss.dao.SysOssDao;
import com.zz.springbootproject.module.oss.service.SysOssService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.springbootproject.util.FileUtils;
import com.zz.springbootproject.utils.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 文件上传 服务实现类
 *
 * @author chenxue
 * @since 2020-07-16
 */
@Service
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {
    @Autowired
    private FileConfig fileConfig;

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page = new Query<SysOssEntity>(params).getPage();
        List<SysOssEntity> list = baseMapper.queryPage(page, params);
        return new PageUtil(page.setRecords(list));
    }

    /**
     * @param name 文件名称
     * @param file 文件
     * @Description: 上传文件
     * @Author: chenxue
     * @Date: 2020/7/25  15:50
     */
    @Override
    public void upload(String name, MultipartFile file) {
        Optional.ofNullable(file).orElseThrow(() -> new ServerException("上传文件失败!"));
        // 校验文件大小
        FileUtils.checkSize(fileConfig.getMaxSize(), file.getSize());
        // 获取文件后缀
        String suffix = FileUtils.getExtensionName(file.getOriginalFilename());
        // 获取文件类型
        String type = FileUtils.getFileType(suffix);
        // 上传文件
        File file1 = FileUtils.upload(file, fileConfig.getPath().getPath() + type + File.separator);
        SysOssEntity sysOssEntity = new SysOssEntity();
        sysOssEntity.setName(StringUtils.isBlank(name) ? FileUtils.getFileNameNoEx(file.getOriginalFilename()) : name);
        sysOssEntity.setRealName(file1.getName());
        sysOssEntity.setSize(FileUtils.getSize(file.getSize()));
        sysOssEntity.setSuffix(suffix);
        sysOssEntity.setType(type);
        sysOssEntity.setUrl(file1.getPath());
        sysOssEntity.setCreateTime(new Date());
        sysOssEntity.setCreateUser(ShiroUtils.getUser().getUserId());
        this.save(sysOssEntity);
    }
}
