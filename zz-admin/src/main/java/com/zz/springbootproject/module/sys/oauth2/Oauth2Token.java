package com.zz.springbootproject.module.sys.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @description:
 * @author: chenxue
 * @create: 2020-05-20 20:00
 **/
public class Oauth2Token implements AuthenticationToken {
    private String token;

    public Oauth2Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
