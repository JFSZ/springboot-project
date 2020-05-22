package com.zz.springbootproject.module.sys.oauth2;

import com.google.gson.Gson;
import com.zz.springbootproject.utils.HttpContextUtils;
import com.zz.springbootproject.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @description: 验证过滤器 shiro 过滤器
 * @author: chenxue
 * @create: 2020-05-20 15:43
 **/
@Slf4j
public class OauthFilter extends AuthenticatingFilter {

    /**
     * @Description: 自定义创建token方法
     * @param request
     * @param response
     * @Author: chenxue
     * @Date: 2020/5/20  19:05
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = getToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return new Oauth2Token(token);
    }

    
    /**
     * @Description: 当被方法isAccessAllowed拒绝访问时，调用此方法，
     * 此方法为处理措施，处理完毕，返回true则继续执行filter链，返回false则不执行。处理措施，比如尝试登陆。
     * @param request
     * @param response
     * @Author: chenxue 
     * @Date: 2020/5/20  19:06
     */ 
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getToken((HttpServletRequest)request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            //httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            String json = new Gson().toJson(ServerResponse.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);
            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * @Description: 登录成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @Author: chenxue
     * @Date: 2020/5/20  19:08
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * @Description: 处理登录失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @Author: chenxue
     * @Date: 2020/5/20  19:08
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        //httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            ServerResponse serverResponse = ServerResponse.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(serverResponse);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }
        return false;
    }

    /**
     * @Description: 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @Author: chenxue
     * @Date: 2020/5/20  19:04
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String path = ((HttpServletRequest) request).getServletPath();
        log.info("请求路径为: " + path);
        if(((HttpServletRequest)request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }

    protected String getToken(HttpServletRequest request){
        //首先从header 中取
        String token = request.getHeader("token");
        //否则从参数中获取
        Optional.ofNullable(token).orElse(request.getParameter("token"));
        return token;
    }

}
