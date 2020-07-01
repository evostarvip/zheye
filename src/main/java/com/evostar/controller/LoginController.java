package com.evostar.controller;

import com.evostar.model.User;
import com.evostar.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@Api(tags = "用户注册/登录")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/reg/"}, method = {RequestMethod.POST})
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "登录账号，手机号或邮箱",dataType = "String",defaultValue = "123@evostar.vip",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",defaultValue = "123456",required = true),
            @ApiImplicitParam(name = "rememberme",value = "是否7天免登录,false|true",dataType = "Boolean",defaultValue = "false")
    })
    public User reg(HttpServletResponse response, String username, String password,
                    @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme) throws IOException {
        User user = new User();
        String usernames = "";
        try {
            Map<String, Object> map = userService.register(username, password,rememberme);
            if (map.containsKey("data")) {
                response.setStatus(200);
                user =  (User) map.get("data");
            } else {
                response.sendError(400,String.valueOf(map.get("msg")));
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            response.sendError(500,"服务器错误");
        }
        return user;
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "登录账号，手机号或邮箱",dataType = "String",defaultValue = "123@evostar.vip",required = true),
            @ApiImplicitParam(name="password",value = "密码",dataType = "String",defaultValue = "123456",required = true),
            @ApiImplicitParam(name="rememberme",value = "开启7天免登录,false|true",dataType = "Boolean",defaultValue = "false")
    })
    public User login(HttpServletResponse response, String username, String password,
                                 @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme) throws IOException {
        User user = new User();
        try {
            Map<String, Object> map = userService.login(username, password,rememberme);
            if (map.containsKey("data")) {
                response.setStatus(200);
                user = (User) map.get("data");
            } else {
                response.sendError(400,String.valueOf(map.get("msg")));
            }
        } catch (Exception e) {
            logger.error("登陆异常" + e.getMessage());
            response.sendError(500,"服务器错误");
        }
        return user;
    }
}
