package com.evostar.dao;

import com.evostar.model.Answer;
import com.evostar.model.Comment;
import com.sun.xml.internal.rngom.ast.builder.CommentList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentDAO {
    @Select({"select * from comment where id=#{id}"})
    public Comment getById(int id);


    @Insert({"insert into comment( content, user_id, entity_id,type,created_date,responded) values (#{content},#{userId},#{entityId},#{type},#{createdDate},#{responded})"})
    public int addComment(Comment comment);


    public List<Comment> getAnswerCommentListById(@Param(value = "entityId") int entityId,
                                                  @Param(value = "type") int type,
                                                  @Param(value = "offset") int offset,
                                                  @Param(value = "limit") int limit);
    @Select("select count(*) from comment where entity_id = #{id} and type = #{type}")
    public int getCountByType(@Param(value = "id") int id, @Param(value = "type") int type);
}
