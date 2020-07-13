package com.evostar.controller;

import com.evostar.VO.ActionsVO;
import com.evostar.VO.AnswerVO;
import com.evostar.VO.IndexVO;
import com.evostar.model.Answer;
import com.evostar.model.Question;
import com.evostar.service.AnswerService;
import com.evostar.service.QuestionService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET})
    @ApiImplicitParam(name = "page", value = "请求第几页，不填默认为1", defaultValue = "1")
    public List<IndexVO> index(@RequestParam(required = false, defaultValue = "1") int page){
        int limit = 10;
        int offset = (page - 1) * limit;
        List<Question> questionList = questionService.getLatestQuestions(0, offset, limit);
        return questionList.stream().map(question -> {
            ActionsVO actionsVO = new ActionsVO();
            actionsVO.setAgreeNum(0);
            IndexVO indexVO = new IndexVO();
            indexVO.setId(question.getId());
            indexVO.setActions(actionsVO);
            indexVO.setTitle(question.getTitle());

            Answer answer = answerService.getLastAnswerByQuestionId(question.getId());
            if(answer != null){
                AnswerVO answerVO = answerService.getAnswerVO(answer);
                indexVO.setDetail(answerVO);
                indexVO.setAnswer(answer.getAnswer());
                String answerContent = answer.getContent().replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");;
                answerContent = answerContent.length() > 50 ? answerContent.substring(0, 50) + "......" : answerContent;
                indexVO.setSummary(answerContent);
            }
            return indexVO;
        }).collect(Collectors.toList());
    }
}
