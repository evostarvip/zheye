package com.evostar.controller;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@Api(tags = "用户注册/登录")
public class LoginController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = {"/reg/"}, method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账号，手机号或邮箱", dataType = "String", defaultValue = "123@evostar.vip", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", defaultValue = "123456", required = true),
            @ApiImplicitParam(name = "rememberme", value = "是否7天免登录,false|true", dataType = "Boolean", defaultValue = "false")
    })
    public String reg(HttpServletResponse response, String username, String password) throws IOException {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.get("msg").equals("注册成功")) {
                return "注册成功";
            } else {
                System.out.println(map.get("msg"));
                response.sendError(400, map.get("msg"));
                return map.get("msg");
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            response.sendError(500,"服务器错误");
            return "服务器错误";
        }
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "登录账号，手机号或邮箱", dataType = "String", defaultValue = "123@evostar.vip", required = true),
            @ApiImplicitParam(name="password", value = "密码",dataType = "String", defaultValue = "123456", required = true),
            @ApiImplicitParam(name="rememberme", value = "开启7天免登录,false|true", dataType = "Boolean", defaultValue = "false")
    })
    public String login(Model model, HttpServletResponse response, String username, String password,
                      @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme) {
        try {
            Map<String, Object> map = userService.login(username, password, rememberme);
            if (map.containsKey("data")) {
                model.addAttribute("msg","登录成功");
                User user = (User) map.get("data");
                Cookie cookie = new Cookie("token",user.getToken());
                cookie.setPath("/");
                cookie.setMaxAge(2 * 60 * 60);//两个小时
                response.addCookie(cookie);
                return "success";
            } else {
                model.addAttribute("msg",String.valueOf(map.get("msg")));
                return "error";
            }
        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            model.addAttribute("msg","服务器错误");
            return "error";
        }
    }

    @RequestMapping(value = "/layout", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    @ResponseBody
    public String layout(HttpServletResponse response){
        Cookie cookie = new Cookie("token","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "success";
    }
}
