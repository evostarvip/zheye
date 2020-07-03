package com.evostar.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
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
    private String headUrl;
    @ApiModelProperty(value = "token，用于后续用户的身份凭证")
    private String token;

    public User() {
    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }
}
