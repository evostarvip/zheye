package com.evostar.controller;

import com.evostar.model.ViewObject;
import com.evostar.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.View;
import java.util.List;

@RestController
@Api(tags = "首页")
public class HomeController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping(path = {"/", "/index"},method = {RequestMethod.GET})
    @ApiImplicitParam(name = "page",value = "请求第几页，不填默认为1",defaultValue = "1")
    public List<ViewObject> index(@RequestParam(value = "page", defaultValue = "1") int page) {
        return questionService.getQuestions(0, page, 10);
    }
}
