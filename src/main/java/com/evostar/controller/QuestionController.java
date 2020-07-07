package com.evostar.controller;

import com.evostar.exception.MyException;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, String> addQuestion(@RequestBody Map<String, String> map){
        try {
            String title = map.get("title");
            String content = map.get("content");
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            question.setUserId(hostHolder.getUser().getId());
            Map<String, String> result = new HashMap<>();
            if(questionService.addQuestion(question) > 0){
                result.put("msg", "SUCCESS");
                return result;
            }else{
                throw new MyException(MsgCodeEnum.OPERATION_FAILED.getCode(), MsgCodeEnum.OPERATION_FAILED.getMsg());
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
            throw new MyException(MsgCodeEnum.OPERATION_FAILED.getCode(), MsgCodeEnum.OPERATION_FAILED.getMsg());
        }
    }


    @ApiOperation(value = "问题详情")
    @ApiImplicitParam(name = "qid", value = "question的id", dataType = "int", defaultValue = "1", required = true)
    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public Question questionDetail(@RequestBody Map<String, String> map) {
        int qid = Integer.parseInt(map.get("qid"));
        Question question =  questionService.getById(qid);
        if (question == null){
            throw new MyException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMsg());
        }
        return question;
    }
}