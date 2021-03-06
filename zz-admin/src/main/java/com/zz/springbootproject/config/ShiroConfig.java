package com.zz.springbootproject.config;

import com.zz.springbootproject.module.sys.oauth2.Oauth2Realm;
import com.zz.springbootproject.module.sys.oauth2.OauthFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Description: shiro配置类
 * @Author: chenxue
 * @Date: 2020/5/14  16:19
 */
@Configuration
public class ShiroConfig {

    /**
     * @param
     * @Description: url过滤器
     * @Author: chenxue
     * @Date: 2020/5/14  18:38
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        //不需要在此处配置权限页面,因为上面的ShiroFilterFactoryBean已经配置过,但是此处必须存在,因为shiro-spring-boot-web-starter或查找此Bean,没有会报错
        return new DefaultShiroFilterChainDefinition();
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤
        Map<String, Filter> filters = shiroFilter.getFilters();
        filters.put("oauth2", new OauthFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/layout", "anon");
        filterMap.put("/sys/captcha.jpg", "anon");
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * @param oauth2Realm
     * @Description: 注意这里需要返回 SessionsSecurityManager 避免 启动报 authoricator not found 错误
     * @Author: chenxue
     * @Date: 2020/5/14  18:39
     */
    @Bean
    @ConditionalOnBean(Oauth2Realm.class)
    public SessionsSecurityManager securityManager(Oauth2Realm oauth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(oauth2Realm);
        return securityManager;
    }

    /**
     * @param
     * @Description: 如果有 shrio 标签权限控制。必须加上，否则请求路径404
     * @Author: chenxue
     * @Date: 2020/5/23  18:42
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @param securityManager
     * @Description: 集成 AOP
     * @Author: chenxue
     * @Date: 2020/5/23  18:43
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
