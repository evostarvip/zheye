package com.evostar.service;

import com.evostar.dao.QuestionDAO;
import com.evostar.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionDAO questionDAO;

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
    public int addQuestion(Question question) {
        question.setTitle(question.getTitle());
        question.setContent(question.getContent());
        int id = questionDAO.addQuestion(question);
        return id > 0 ? id : 0;
    }
}
