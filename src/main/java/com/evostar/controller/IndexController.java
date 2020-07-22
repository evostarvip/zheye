package com.evostar.controller;


import com.evostar.annotation.Token;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @Token
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model){
        return "/index";
    }
}
