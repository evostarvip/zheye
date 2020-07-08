package com.evostar.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestCookie {
    @RequestMapping(value = "/test")
    public String test(Model model){
        return "success";
    }
}
