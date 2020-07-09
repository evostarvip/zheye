package com.evostar.controller;

import com.evostar.model.HostHolder;
import com.evostar.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "获取用户登录状态")
public class UserController {

    @Autowired
    private HostHolder hostHolder;
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户登录状态")
    public User user(){
        return hostHolder.getUser();
    }
}
