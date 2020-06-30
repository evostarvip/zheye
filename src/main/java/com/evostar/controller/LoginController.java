package com.evostar.controller;

import com.evostar.model.Response;
import com.evostar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/reg/"}, method = {RequestMethod.POST})
    @ResponseBody
    public Response reg(Model model,String username, String password,
                      @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme) {
        Response response = new Response();
        try {
            Map<String, Object> map = userService.register(username, password,rememberme);
            if (map.containsKey("token")) {
                response.setStatus(200);
                response.setMsg("注册成功");
                response.setData(map);
            } else {
                response.setStatus(200);
                response.setMsg(String.valueOf(map.get("msg")));
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            response.setStatus(500);
            response.setMsg("服务器错误");
        }
        return response;
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    @ResponseBody
    public Response login(Model model,String username, String password,
                        @RequestParam(value="rememberme", defaultValue = "false") boolean rememberme) {
        Response response = new Response();
        try {
            Map<String, Object> map = userService.login(username, password,rememberme);
            if (map.containsKey("token")) {
                response.setMsg("登录成功");
                response.setStatus(200);
                response.setData(map);
            } else {
                response.setMsg(String.valueOf(map.get("msg")));
                response.setStatus(400);
            }
        } catch (Exception e) {
            response.setMsg("登陆异常");
            response.setStatus(500);
            logger.error("登陆异常" + e.getMessage());
        }
        return response;
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {
        //userService.logout(ticket);
        return "redirect:/";
    }
}
