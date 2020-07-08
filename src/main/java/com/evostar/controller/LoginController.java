package com.evostar.controller;

import com.evostar.exception.MyException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.User;
import com.evostar.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "用户注册/登录")
public class LoginController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = {"/reg"}, method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账号，手机号或邮箱", dataType = "String", defaultValue = "123@evostar.vip", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", defaultValue = "123456", required = true),
            @ApiImplicitParam(name = "rememberme", value = "是否7天免登录,false|true", dataType = "Boolean", defaultValue = "false")
    })
    public Map<String, String> reg(@RequestBody Map<String, String> map) throws Exception {
        String username = map.get("username");
        String password = map.get("password");
        System.out.println("username:"+username+",password:"+password);
        if(userService.register(username, password) > 0){
            Map<String, String> result = new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        }else{
            throw new MyException(MsgCodeEnum.REGISTERED_FAILED.getCode(), MsgCodeEnum.REGISTERED_FAILED.getMsg());
        }
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账号，手机号或邮箱", dataType = "String", defaultValue = "123@evostar.vip", required = true),
            @ApiImplicitParam(name="password", value = "密码",dataType = "String", defaultValue = "123456", required = true),
            @ApiImplicitParam(name="rememberme", value = "开启7天免登录,false|true", dataType = "Boolean", defaultValue = "false")
    })
    public Map<String, String> login(@RequestBody Map<String, String> map, HttpServletResponse response) {
        String username = map.get("username");
        String password = map.get("password");
        Boolean rememberme = Boolean.valueOf(map.get("rememberme"));
        User user = userService.login(username, password, rememberme);
        Map<String, String> result = new HashMap<>();
        Cookie cookie = new Cookie("token",user.getToken());
        cookie.setDomain("localhost:8080");
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60 * 7);//两个小时
        response.addCookie(cookie);
        result.put("msg", "SUCCESS");
        result.put("redirect", "http:baidu.com");
        result.put("headUrl",user.getHeadUrl());
        return result;
    }

    @RequestMapping(value = "/layout", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    @ResponseBody
    public Map<String, String> layout(HttpServletResponse response){
        Cookie cookie = new Cookie("token","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        Map<String, String> result = new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }
}
