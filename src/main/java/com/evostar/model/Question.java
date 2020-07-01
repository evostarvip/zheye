package com.evostar.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;

import java.util.Date;

@ApiModel
public class Question {
    @ApiModelProperty(value = "问题的id")
    private int id;
    @ApiModelProperty(value = "问题的title")
    private String title;
    @ApiModelProperty(value = "问题的描述")
    private String content;
    @ApiModelProperty(value = "创建问题的时间")
    private Date created_date;
    @ApiModelProperty(value = "创建问题的userId")
    private int user_id;
    @ApiModelProperty(value = "评论的数量")
    private int comment_count;
    @ApiModelProperty(value = "点赞的数量")
    private int support_count;
    @ApiModelProperty(value = "踩的数量")
    private int unsupport_count;

    public void setSupport_count(int support_count) {
        this.support_count = support_count;
    }

    public void setUnsupport_count(int unsupport_count) {
        this.unsupport_count = unsupport_count;
    }

    public int getSupport_count() {
        return support_count;
    }

    public int getUnsupport_count() {
        return unsupport_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
