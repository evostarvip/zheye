package com.evostar.service;

import com.evostar.dao.QuestionDAO;
import com.evostar.model.Answer;
import com.evostar.model.Question;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private SensitiveService sensitiveService;


    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }


    public int addQuestion(Question question) {
        question.setTitle(question.getTitle());
        question.setContent(question.getContent());

        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        int id = questionDAO.addQuestion(question);
        return id > 0 ? id : 0;
    }

    public Question getById(int id) {
        return questionDAO.getById(id);
    }
}
