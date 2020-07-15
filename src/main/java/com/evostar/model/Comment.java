package com.evostar.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int id;
    private String content;
    private int userId;
    private byte entityId;
    private int type;
    private Date createDate;
    private byte status;
    private int responded;
}
