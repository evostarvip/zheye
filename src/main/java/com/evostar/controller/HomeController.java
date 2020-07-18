package com.evostar.controller;

import com.evostar.VO.ActionsVO;
import com.evostar.VO.AnswerVO;
import com.evostar.VO.IndexVO;
import com.evostar.exception.ServiceException;
import com.evostar.model.Answer;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import com.evostar.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "首页")
public class HomeController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private SupportService supportService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private EsService esService;

    @RequestMapping(path = {"/index"}, method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "请求第几页，不填默认为1", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "search", value = "关键词搜索", required = false)
    })
    public List<IndexVO> index(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String search){
        int limit = 10;
        int offset = (page - 1) * limit;
        List<Question> questionList = questionService.getLatestQuestions(0, search, offset, limit);
        return this.questionVO(questionList);
    }

    @ApiImplicitParam(name = "search", value = "关键词搜索", required = false)
    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public List<IndexVO> search(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(required = false) String search){
        int limit = pageable.getPageSize();
        List<Question> questionList = esService.selectByTitleLike(search, pageable);
        return this.questionVO(questionList);
    }
    @ApiOperation(value = "获取关注列表")
    @RequestMapping(path = {"/followList"}, method = {RequestMethod.GET})
    @ApiImplicitParam(name = "page", value = "请求第几页，不填默认为1", required = false, defaultValue = "1")
    public List<IndexVO> followList(@RequestParam(required = false, defaultValue = "1") int page){
        int limit = 10;
        int offset = (page - 1) * limit;
        List<String> userList = followService.getFollowedUser(hostHolder.getUser().getId());
        if(userList.size() == 0){
            throw new ServiceException(MsgCodeEnum.DATA_NONE);
        }
        List<Question> questionList = questionService.getLatestFollowedQuestions(userList, offset, limit);
        return this.questionVO(questionList);
    }

    public List<IndexVO> questionVO(List<Question> questionList){
        return questionList.stream().map(question -> {
            ActionsVO actionsVO = new ActionsVO();
            actionsVO.setAgreeNum(supportService.supportNum(question.getId(), 1));
            if(hostHolder.getUser() != null){
                actionsVO.setDisagree(supportService.isUnSupport(question.getId(), 1, hostHolder.getUser().getId()));
                actionsVO.setIsAgree(supportService.isSupport(question.getId(), 1, hostHolder.getUser().getId()));
            }
            IndexVO indexVO = new IndexVO();
            indexVO.setId(question.getId());
            indexVO.setActions(actionsVO);
            indexVO.setTitle(question.getTitle());

            Answer answer = answerService.getLastAnswerByQuestionId(question.getId());
            if(answer != null){
                AnswerVO answerVO = answerService.getAnswerVO(answer);
                indexVO.setDetail(answerVO);
                indexVO.setAnswer(userService.getUserVO(answer.getAnswer()));
                String answerContent = answer.getContent().replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");;
                answerContent = answerContent.length() > 50 ? answerContent.substring(0, 50) + "......" : answerContent;
                indexVO.setSummary(answerContent);
            }
            return indexVO;
        }).collect(Collectors.toList());
    }
}
