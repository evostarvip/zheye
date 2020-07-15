package com.evostar.controller;

import com.evostar.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags = "评论")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "评论")
    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "被回复的id", defaultValue = "1", dataType = "int", required = true),
            @ApiImplicitParam(name = "type", value = "1是回复answer，2是回复评论", defaultValue = "1", dataType = "int", required = true),
            @ApiImplicitParam(name = "content", value = "回复的内容", defaultValue = "你好", dataType = "string", required = true),

    })
    public void comment(Map<String, String> map){
        int id = Integer.parseInt(map.get("id"));
        int type = Integer.parseInt(map.get("type"));
        String content = map.get("content");
        //commentService.addComment(id, type, content);
    }
}
