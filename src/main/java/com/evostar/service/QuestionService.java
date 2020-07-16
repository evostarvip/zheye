package com.evostar.service;

import com.evostar.dao.QuestionDAO;
import com.evostar.exception.ServiceException;
import com.evostar.model.MsgCodeEnum;
import com.evostar.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private QuestionDAO questionDAO;
    public int addQuestion(Question question){
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question);
    }


    public void checkQid(int questionId){
        if (questionId == 0) {
            throw new ServiceException(MsgCodeEnum.PARAM_EMPTY.getCode(), "qid" + MsgCodeEnum.PARAM_EMPTY.getMessage());
        }
        //查询问题是否存在
        Question question = questionDAO.getById(questionId);
        if (question == null) {
            throw new ServiceException(MsgCodeEnum.DATA_NONE.getCode(), MsgCodeEnum.DATA_NONE.getMessage());
        }
    }

    public List<Question> getLatestQuestions(int userId, String search, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, search, offset, limit);
    }

    public Question getById(int qid){
        return questionDAO.getById(qid);
    }
}
