package com.evostar.VO;

import com.evostar.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {
    @ApiModelProperty(value = "回复的人")
    private User user;

    @ApiModelProperty(value = "被回复的人")
    private User responder;

    @ApiModelProperty(value = "时间")
    private Date time;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "点赞的数量")
    private int likeNum;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLike;

    @ApiModelProperty(value = "是否点踩")
    private Boolean isDisLike;

    @ApiModelProperty(value = "回复的数据")
    private List<CommentVO> replies;
}
