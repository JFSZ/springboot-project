package com.zz.springbootproject.module.sys.controller;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.utils.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @GetMapping("/info")
    public ServerResponse info(){
        return ServerResponse.ok().put("user",new SysUserEntity());
    }
}
