package com.zz.springbootproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 解决跨域问题
 * @Author: chenxue
 * @Date: 2020/5/14  16:20
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private FileConfig fileConfig;

    public CorsConfig(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

    /**
     * @param registry
     * @Description: 配置资源路径
     * @Author: chenxue
     * @Date: 2020/7/27  11:19
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        FileConfig.ElPath path = fileConfig.getPath();
        String path1 = "file:" + path.getPath().replace("\\", "/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/statics/")
                .addResourceLocations(path1);
    }
}