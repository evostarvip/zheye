package com.evostar.service;

import com.evostar.dao.AnswerDAO;
import com.evostar.model.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    @Autowired
    private AnswerDAO answerDAO;

    public Answer getLastAnswerByQuestionId(int questionId){
        return answerDAO.getLastAnswerByQuestionId(questionId);
    }
}
