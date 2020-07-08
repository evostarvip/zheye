package com.evostar.vo;

import com.evostar.model.Answer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDetailVO {
    @ApiModelProperty(value = "问题id")
    private int id;
    @ApiModelProperty(value = "问题的title")
    private String title;
    @ApiModelProperty(value = "问题的简述")
    private String summary;
    @ApiModelProperty(value = "问题的完整描述")
    private String detail;
    @ApiModelProperty(value = "回答的数据")
    private List<Answer> answerList;
}
