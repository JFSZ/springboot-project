package com.zz.springbootproject.module.sys.controller;

import com.zz.springbootproject.module.sys.service.SysCaptchaService;
import com.zz.springbootproject.utils.ServerResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Autowired
    private SysCaptchaService sysCaptchaService;

    /**
     * @Description: 获取验证码
     * @param
     * @Author: chenxue
     * @Date: 2020/5/11  15:47
     */
    @GetMapping("/captcha.jpg")
    public void getCaptchaCode(HttpServletResponse response,String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @PostMapping("/login")
    public ServerResponse login(){
        return ServerResponse.ok().put("token","123");
    }

}
