package com.evostar.controller;

import com.evostar.exception.MyException;
import com.evostar.model.Answer;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import com.evostar.service.AnswerService;
import com.evostar.service.QuestionService;
import com.evostar.service.UserService;
import com.evostar.vo.ActionsVO;
import com.evostar.vo.IndexVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(tags = "首页")
public class HomeController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerService answerService;

    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET})
    @ApiImplicitParam(name = "page", value = "请求第几页，不填默认为1", defaultValue = "1")
    public List<IndexVO> index(@RequestParam(required = false, defaultValue = "1") int page) {
        int limit = 10;
        int offset = (page - 1) * limit;
        List<Question> questionList = questionService.getLatestQuestions(0, offset, limit);
        //如果questionList没有数据，停止执行直接返回空数据
        if(questionList == null){
            throw new MyException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMsg());
        }
        return questionList.stream().map(question -> {
            Answer answer = answerService.getLastAnswerByQuestionId(question.getId());
            IndexVO indexVO = new IndexVO();
            indexVO.setId(question.getId());
            indexVO.setTitle(question.getTitle());
            if(answer != null){
                indexVO.setAnswer(answer.getAnswer());
                String answerContent = answer.getContent().replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");;
                answerContent = answerContent.length() > 30 ? answerContent.substring(0,30)+"......" : answerContent;
                indexVO.setSummary(answerContent);
            }
            ActionsVO actionsVO = new ActionsVO();
            //暂留，后面开发点赞、评论时填充值
            actionsVO.setCollect(false);
            actionsVO.setAgreeNum(0);
            actionsVO.setDisagree(false);
            actionsVO.setIsAgree(false);
            actionsVO.setLike(false);
            actionsVO.setReviewNum(0);
            indexVO.setActions(actionsVO);
            return indexVO;
        }).collect(Collectors.toList());
    }
}
