package com.evostar.dao;

import com.evostar.model.Answer;
import com.evostar.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AnswerDAO {
    String TABLE_NAME = " answer";
    String INSERT_FIELDS = " user_id, question_id, content,created_date ";
    String SELECT_FIELDS = " * ";
    public Answer getLastAnswerByQuestionId(int questionId);

    //增加回答
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{questionId},#{content},#{createdDate})"})
    public int addAnswer(Answer answer);

    @Select({"select * from ",TABLE_NAME," where question_id = #{qid} " +
            "order by id desc limit #{offset},#{limit}",})
    public List<Answer> getAnswerListByQid(
            @Param(value = "qid") int qid, @Param(value = "offset") int offset,
            @Param("limit") int limit);
}
