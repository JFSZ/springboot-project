package com.zz.springbootproject.config;

import com.zz.springbootproject.module.sys.oauth2.Oauth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: shiro配置类
 * @Author: chenxue
 * @Date: 2020/5/14  16:19
 */
@Configuration
public class ShiroConfig {

    /**
     * @Description: 过滤器
     * @param
     * @Author: chenxue
     * @Date: 2020/5/14  18:38
     */
    @Bean("shiroFilter")
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/sys/login","anno");
        definition.addPathDefinition("/sys/layout","anno");
        definition.addPathDefinition("/sys/captcha.jpg","anno");
        definition.addPathDefinition("/**","authc");
        return definition;
    }

    /**
     * @Description: 安全管理器
     * @param oauth2Realm
     * @Author: chenxue 
     * @Date: 2020/5/14  18:39
     */ 
    @Bean("/securityManager")
    @ConditionalOnBean(Oauth2Realm.class)
    public SecurityManager securityManager(Oauth2Realm oauth2Realm){
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
