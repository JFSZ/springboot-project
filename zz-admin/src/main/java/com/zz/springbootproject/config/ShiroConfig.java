package com.zz.springbootproject.config;

import com.zz.springbootproject.module.sys.oauth2.Oauth2Realm;
import com.zz.springbootproject.module.sys.oauth2.OauthFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description: shiro配置类
 * @Author: chenxue
 * @Date: 2020/5/14  16:19
 */
@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm(){
        return new Oauth2Realm();
    }

    /**
     * @Description: url过滤器
     * @param
     * @Author: chenxue
     * @Date: 2020/5/14  18:38
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        //不需要在此处配置权限页面,因为上面的ShiroFilterFactoryBean已经配置过,但是此处必须存在,因为shiro-spring-boot-web-starter或查找此Bean,没有会报错
        return new DefaultShiroFilterChainDefinition();
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤
        Map<String, Filter> filters = shiroFilter.getFilters();
        filters.put("oauth2", new OauthFilter());
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/sys/login","anon");
        filterMap.put("/sys/layout","anon");
        filterMap.put("/sys/captcha.jpg","anon");
        filterMap.put("/**","oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * @Description: 注意这里需要返回 SessionsSecurityManager 避免 启动报 authoricator not found 错误
     * @param oauth2Realm
     * @Author: chenxue 
     * @Date: 2020/5/14  18:39
     */ 
    @Bean
    @ConditionalOnBean(Oauth2Realm.class)
    public SessionsSecurityManager securityManager(Realm oauth2Realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(oauth2Realm);
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
