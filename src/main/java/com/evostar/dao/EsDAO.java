package com.evostar.dao;

import com.evostar.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EsDAO  extends CrudRepository<Question, Integer> {
    public List<Question> findQuestionsByTitleLikeOrContentLike(String title, String content);

    public List<Question> findQuestionsById(int id);

    public List<Question> findQuestionsByTitleContains(String title, Pageable page);

    public void deleteQuestionsByTitleContaining(String title);
}
