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
    public Answer getLastAnswerByQuestionId(int questionId);

    //增加回答
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{questionId},#{content},#{createdDate})"})
    public int addAnswer(Answer answer);

    public List<Answer> getAnswerListByQidDesc(@Param(value = "id") int qid, @Param(value = "offset") int offset,
            @Param("limit") int limit);

    public Answer getAnswerById(int aid);

    @Select({"select count(*) from ",TABLE_NAME," where question_id = #{qid}"})
    public int getCountByQid(@Param(value = "qid") int qid);
}
