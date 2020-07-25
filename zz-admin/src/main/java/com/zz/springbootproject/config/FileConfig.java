package com.zz.springbootproject.config;

import com.zz.springbootproject.common.Constant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 文件配置
 * @author: chenxue
 * @create: 2020-07-25 16:01
 **/
@Configuration
@ConfigurationProperties(prefix = "zzadmin.file")
@Data
public class FileConfig {
    /**
     * 文件大小限制
     */
    private Long maxSize;

    private ElPath windows;

    private ElPath linux;

    private ElPath mac;

    public ElPath getPath() {
        String osName = System.getProperty("os.name");
        if (StringUtils.isNotBlank(osName)) {
            if (osName.toLowerCase().startsWith(Constant.WIN)) {
                return windows;
            } else if (osName.toLowerCase().startsWith(Constant.MAC)) {
                return mac;
            }
        }
        return linux;
    }

    @Data
    public class ElPath {
        String path;
    }
}
