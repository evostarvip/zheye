package com.evostar.controller;

import com.evostar.VO.CommentVO;
import com.evostar.exception.ServiceException;
import com.evostar.model.Comment;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.service.CommentService;
import com.evostar.service.SupportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(tags = "评论")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SupportService supportService;

    @ApiOperation(value = "评论")
    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "被回复的id", defaultValue = "1", dataType = "int", required = true),
            @ApiImplicitParam(name = "type", value = "1是回复answer，2是回复评论", defaultValue = "1", dataType = "int", required = true),
            @ApiImplicitParam(name = "content", value = "回复的内容", defaultValue = "你好", dataType = "string", required = true),

    })
    public HashMap<String, String> comment(@RequestBody Map<String, String> map){
        System.out.println(map);
        int id = Integer.parseInt(map.get("id"));
        int type = Integer.parseInt(map.get("type"));
        String content = map.get("content");
        commentService.checkIsExist(id, type);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setEntityId(id);
        comment.setType(type);
        comment.setUserId(hostHolder.getUser().getId());
        if(type == 2){
            Comment comment1 = commentService.getById(id);
            comment.setResponded(comment1.getUserId());
        }
        if(commentService.addComment(comment) > 0){
            HashMap<String, String> result =new HashMap<>();
            result.put("msg", "SUCCESS");
            return result;
        }else {
            throw new ServiceException(MsgCodeEnum.OPERATION_FAILED);
        }
    }

    @ApiOperation(value = "获取评论")
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "被回复的id", defaultValue = "1", dataType = "int", required = true),
            @ApiImplicitParam(name = "type", value = "1是回复answer，2是回复评论", defaultValue = "1", dataType = "int", required = true)
    })
    public List<CommentVO> getComment(int id, int type, @RequestParam(required = false, defaultValue = "1") int page){
        return this.getCommentList(id, type, page);
    }
    public List<CommentVO> getCommentList(int id, int type, int page){
        int limit = 20;
        int offset = (page - 1) * limit;
        List<Comment> list = commentService.getAnswerCommentListById(id, type, offset, limit);
        return list.stream().map(comment -> {
            CommentVO commentVO = new CommentVO();
            commentVO.setContent(comment.getContent());
            commentVO.setIsDisLike(supportService.isUnSupport(comment.getId(),3,hostHolder.getUser().getId()));
            commentVO.setIsLike(supportService.isUnSupport(comment.getId(), 3, hostHolder.getUser().getId()));
            commentVO.setLikeNum(supportService.supportNum(comment.getId(), 3));
            commentVO.setUser(comment.getUser());
            commentVO.setTime(comment.getCreatedDate());
            commentVO.setResponder(comment.getResponder());
            if(type == 1){
                List<CommentVO> replies = this.getCommentList(comment.getId(),2, 1);
                commentVO.setReplies(replies);
            }
            return commentVO;
        }).collect(Collectors.toList());
    }
}
