package com.zz.springbootproject.module.sys.controller;

import com.zz.springbootproject.module.sys.entity.SysUserEntity;
import com.zz.springbootproject.module.sys.service.SysCaptchaService;
import com.zz.springbootproject.module.sys.service.SysUserService;
import com.zz.springbootproject.module.sys.vo.SysLoginVo;
import com.zz.springbootproject.util.ServerResponse;
import com.zz.springbootproject.utils.RedisUtils;
import com.zz.springbootproject.utils.ShiroUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param
     * @Description: 获取验证码
     * @Author: chenxue
     * @Date: 2020/5/11  15:47
     */
    @GetMapping("/captcha.jpg")
    public void getCaptchaCode(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * @param sysLoginVo
     * @Description: 登录
     * @Author: chenxue
     * @Date: 2020/5/22  13:33
     */
    @PostMapping("/login")
    public ServerResponse login(@RequestBody SysLoginVo sysLoginVo) {
        // 登录逻辑 1、验证码是否正确 2、账号、密码是否正确 3、账号是否正常
        boolean result = sysCaptchaService.validate(sysLoginVo.getUuid(), sysLoginVo.getCaptcha());
        if (!result) {
            return ServerResponse.error("验证码不正确");
        }
        SysUserEntity sysUserEntity = sysUserService.queryByUserName(sysLoginVo.getUsername());
        //账号、密码不正确
        if (Objects.isNull(sysUserEntity) || StringUtils.isBlank(sysUserEntity.getUsername())
                || !sysUserEntity.getPassword().equals(new Sha256Hash(sysLoginVo.getPassword(), sysUserEntity.getSalt()).toHex())) {
            return ServerResponse.error("账号或密码不正确!");
        }
        //账号被禁用
        if (!sysUserEntity.getStatus()) {
            return ServerResponse.error("账号已被锁定,请联系管理员");
        }
        //生成token,保存数据库/ redis 缓存
        return sysUserService.createToken(sysUserEntity.getUserId());
    }


    /**
     * @param
     * @Description: 退出
     * @Author: chenxue
     * @Date: 2020/5/23  17:59
     */
    @RequestMapping("/logout")
    public ServerResponse logout() {
        // 登出需要清除token。并且redis需要设置淘汰策略
        Object cacheToken = redisUtils.get(Objects.toString(ShiroUtils.getUser().getUserId()));
        redisUtils.del(Objects.toString(cacheToken));
        return sysUserService.lagout();
    }
}
