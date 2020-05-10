package com.zz.springbootproject.module.sys.controller;

import com.zz.springbootproject.utils.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/sys")
public class SysLoginController {
    @PostMapping("/login")
    public ServerResponse login(){
        return ServerResponse.ok().put("token","123");
    }

}
