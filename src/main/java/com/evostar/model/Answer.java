package com.evostar.model;

import lombok.Data;

import java.util.Date;

@Data
public class Answer {
    private int id;
    private int userId;
    private int questionId;
    private Date createdDate;
    private int reviewNum;
    private String content;
    private int commentCount;
    private int supportCount;
    private int unsupportCount;
}
