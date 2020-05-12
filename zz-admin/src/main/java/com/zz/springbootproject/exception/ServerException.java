package com.zz.springbootproject.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 自定义异常
 * @author: chenxue
 * @create: 2020-05-11 18:15
 **/
@Setter
@Getter
public class ServerException extends RuntimeException {
    private String msg;
    private int code = 500;

    public ServerException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServerException( String msg, Throwable e) {
        super(msg,e);
        this.msg = msg;
    }

    public ServerException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ServerException( String msg, int code,Throwable e) {
        super(msg,e);
        this.msg = msg;
        this.code = code;
    }
}
