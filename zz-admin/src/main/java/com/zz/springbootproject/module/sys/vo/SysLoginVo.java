package com.zz.springbootproject.module.sys.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description: 登录vo
 * @author: chenxue
 * @create: 2020-05-21 09:06
 **/
@Data
public class SysLoginVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空!")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    private String password;

    /**
     * uuid
     */
    @NotBlank(message = "uuid不能为空!")
    private String uuid;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空!")
    private String captcha;

}
