package com.evostar.controller;

import com.evostar.exception.MyException;
import com.evostar.model.Answer;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import com.evostar.service.AnswerService;
import com.evostar.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "问题question")
public class QuestionController {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

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
        String qid = map.get("qid");
        if(qid == null){
            throw new MyException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid"+MsgCodeEnum.PARAM_EMPTY.getMsg());
        }
        int questionId = Integer.parseInt(qid);
        Question question =  questionService.getById(questionId);
        if (question == null){
            throw new MyException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMsg());
        }
        List<Answer> answerList = answerService.getAnswerListByQid(questionId, 20, 1);
        return question;
    }

    @ApiOperation(value = "回答问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "回答问题的内容", dataType = "String", defaultValue = "你的问题非常好", required = true),
            @ApiImplicitParam(name = "qid", value = "问题id", dataType = "int", defaultValue = "1", required = true)
    })
    @RequestMapping(value = "/answer/add", method = {RequestMethod.POST})
    public Map<String, String> addAnswer(@RequestBody Map<String, String> map){
        String content = map.get("content");
        String qid = map.get("qid");
        if(qid == null){
            throw new MyException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid"+MsgCodeEnum.PARAM_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(content)){
            throw new MyException(MsgCodeEnum.PARAM_EMPTY.getCode(), "content"+MsgCodeEnum.PARAM_EMPTY.getMsg());
        }
        int questionId = Integer.parseInt(qid);
        answerService.checkQid(questionId);
        Answer answer = new Answer();
        answer.setUserId(hostHolder.getUser().getId());
        answer.setContent(content);
        answer.setQuestionId(questionId);
        answer.setCreatedDate(new Date());
        int res = answerService.addAnswer(answer);
        Map<String, String> result = new HashMap<>();
        if(res > 0){
            result.put("msg", "SUCCESS");
            return result;
        }else{
            throw new MyException(MsgCodeEnum.OPERATION_FAILED.getCode(), MsgCodeEnum.OPERATION_FAILED.getMsg());
        }
    }

    @ApiOperation(value = "获取question的回答列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "qid", value = "question的id", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "page", value = "分页，不填默认1", dataType = "int", defaultValue = "1")
    })
    @RequestMapping(value = "answerList", method = {RequestMethod.GET})
    public List<Answer> answerList(int qid, @RequestParam(required = false,defaultValue = "1") int page){
        int limit = 20;
        int offset = (page - 1) * limit;
        return answerService.getAnswerListByQid(qid, offset, limit);
    }
}