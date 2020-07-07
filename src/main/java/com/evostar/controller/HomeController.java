package com.evostar.controller;

import com.evostar.exception.MyException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import com.evostar.model.User;
import com.evostar.service.QuestionService;
import com.evostar.service.UserService;
import com.evostar.vo.IndexVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "首页")
public class HomeController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ApiImplicitParam(name = "page", value = "请求第几页，不填默认为1", defaultValue = "1")
    public List<IndexVO> index(HttpServletResponse response, @RequestParam(value = "page", defaultValue = "1") int page) {
        int limit = 10;
        int offset = (page - 1) * limit;
        List<Question> questionList = questionService.getLatestQuestions(0, offset, limit);

        //如果questionList没有数据，停止执行直接返回空数据
        if(questionList == null){
            throw new MyException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMsg());
        }
        List<Integer> userIdList = questionList.stream().map(Question::getUserId).collect(Collectors.toList());
        List<User> userList = userService.getUserByIds(userIdList);
        return questionList.stream().map(question -> {
            IndexVO indexVO = new IndexVO();
            indexVO.setQuestion(question);
            indexVO.setFollowCount(0);
            for(User user:userList){
                if(question.getUserId() == user.getId()){
                    indexVO.setUser(user);
                    break;
                }
            }
            return indexVO;
        }).collect(Collectors.toList());
    }
}
