package com.evostar.dao;

import com.evostar.model.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AnswerDAO {
    public Answer getLastAnswerByQuestionId(int questionId);
}
