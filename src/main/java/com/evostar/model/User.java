package com.evostar.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class User {
    @ApiModelProperty(value = "用户id，请忽略")
    private int id;
    @ApiModelProperty(value = "用户登录名")
    private String name;
    @ApiModelProperty(value = "密码，请忽略")
    private String password;
    @ApiModelProperty(value = "请忽略")
    private String salt;
    @ApiModelProperty(value = "头像")
    private String head_url;
    @ApiModelProperty(value = "token，用于后续用户的身份凭证")
    private String token;

    public User() {
    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.head_url = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getHead_url() {
        return head_url;
    }

    public String getToken() {
        return token;
    }
}
