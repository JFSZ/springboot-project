package com.zz.springbootproject.util;

import cn.hutool.core.io.FileUtil;
import com.zz.springbootproject.exception.ServerException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-07-25 16:20
 **/
public class FileUtils extends FileUtil {

    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;

    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;

    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * @param maxSize
     * @param size
     * @Description: 校验文件大小 单位MB
     * @Author: chenxue
     * @Date: 2020/7/25  16:23
     */
    public static void checkSize(long maxSize, long size) {
        if (size > maxSize * MB) {
            throw new ServerException("文件超出规定大小");
        }
    }

    /**
     * @param fileName
     * @Description: 获取文件扩展名 不带.
     * @Author: chenxue
     * @Date: 2020/7/25  16:44
     */
    public static String getExtensionName(String fileName) {
        if (StringUtils.isNotBlank(fileName) && fileName.length() > 0) {
            int index = fileName.lastIndexOf('.');
            if (index > -1 && (index < (fileName.length() - 1))) {
                return fileName.substring(index + 1);
            }
        }
        return fileName;
    }

    /**
     * @param fileName
     * @Description: 获取不带后缀的文件名
     * @Author: chenxue
     * @Date: 2020/7/25  17:34
     */
    public static String getFileNameNoEx(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot > -1) && (dot < (fileName.length()))) {
                return fileName.substring(0, dot);
            }
        }
        return fileName;
    }

    /**
     * @param type
     * @Description: 获取文件类型
     * @Author: chenxue
     * @Date: 2020/7/25  16:50
     */
    public static String getFileType(String type) {
        String documents = "txt doc pdf ppt pps xlsx xls docx";
        String music = "mp3 wav wma mpa ram ra aac aif m4a";
        String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg";
        if (image.contains(type)) {
            return "图片";
        } else if (documents.contains(type)) {
            return "文档";
        } else if (music.contains(type)) {
            return "音乐";
        } else if (video.contains(type)) {
            return "视频";
        } else {
            return "其他";
        }
    }

    /**
     * @param size
     * @Description: 获取文件大小
     * @Author: chenxue
     * @Date: 2020/7/25  17:14
     */
    public static String getSize(long size) {
        String resultSize;
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

    /**
     * @param file
     * @param path
     * @Description: 上传文件
     * @Author: chenxue
     * @Date: 2020/7/25  17:23
     */
    public static File upload(MultipartFile file, String path) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String name = getFileNameNoEx(file.getOriginalFilename());
        String suffix = getExtensionName(file.getOriginalFilename());
        String fileName = path + name + "-" + formatter.format(LocalDateTime.now()) + "." + suffix;
        try {
            File newFile = new File(fileName).getCanonicalFile();
            if (!newFile.getParentFile().exists()) {
                if (!newFile.getParentFile().mkdirs()) {
                    throw new ServerException("上传文件失败!");
                }
            }
            file.transferTo(newFile);
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new Date());
    }
}
