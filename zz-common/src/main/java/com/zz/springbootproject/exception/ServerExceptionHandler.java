package com.zz.springbootproject.exception;

import com.zz.springbootproject.util.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局统一异常处理器
 * @author: chenxue
 * @create: 2020-05-11 18:10
 **/
@RestControllerAdvice
@Slf4j
public class ServerExceptionHandler {

    @ExceptionHandler(ServerException.class)
    public ServerResponse handlerServerException(ServerException e){
        ServerResponse response = new ServerResponse();
        response.put("code",e.getCode());
        response.put("msg",e.getMsg());
        return response;
    }

    @ExceptionHandler(AuthorizationException.class)
    public ServerResponse handleAuthorizationException(AuthorizationException e){
        log.error(e.getMessage(), e);
        return ServerResponse.error(e.getMessage());
    }

}
