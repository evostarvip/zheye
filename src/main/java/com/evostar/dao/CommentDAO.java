package com.evostar.dao;

import com.evostar.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommentDAO {
    @Select({"select * from comment where id=#{id}"})
    public Comment getById(int id);
}
