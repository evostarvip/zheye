package com.evostar.controller;

import com.evostar.model.HostHolder;
import com.evostar.model.Question;
import com.evostar.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@RestController
@Api(tags = "问题question")
public class QuestionController {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);


    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ApiOperation(value = "发布问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "问题的名称", required = true),
            @ApiImplicitParam(name = "content", value = "问题的描述", required = false)

    })
    public String addQuestion(HttpServletResponse response, String title, String content){
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            question.setUserId(hostHolder.getUser().getId());

            if(questionService.addQuestion(question) > 0){
                response.setStatus(200);
                return "success";
            }else{
                response.setStatus(400);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
            response.setStatus(500);
        }
        return "error";
    }
}