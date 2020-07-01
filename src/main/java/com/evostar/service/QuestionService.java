package com.evostar.service;

import com.evostar.dao.QuestionDAO;
import com.evostar.model.Question;
import com.evostar.model.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionDAO questionDAO;

    public List<ViewObject> getQuestions(int userId, int page, int limit) {
        int offset = (page-1)*limit;
        List<Question> questionList = getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", 0);
            vo.set("user", userService.getUser(question.getUser_id()));
            vos.add(vo);
        }
        return vos;
    }
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId,offset, limit);
    }
}
