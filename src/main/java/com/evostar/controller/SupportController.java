package com.evostar.controller;

import com.evostar.exception.ServiceException;
import com.evostar.model.HostHolder;
import com.evostar.model.MsgCodeEnum;
import com.evostar.service.SupportService;
import com.evostar.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Api(tags = "点赞互动")
public class SupportController {
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private SupportService supportService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "点赞")
    @RequestMapping(path = {"/support"}, method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要点赞的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1是给question点赞，2是给answer，3是给comment", required = true, defaultValue = "1")
    })
    public HashMap<String, String> support(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        //判断当前数据是否存在
        if(supportService.checkIsExist(id, type)){
            supportService.support(id, type, hostHolder.getUser().getId());
        }
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    @ApiOperation(value = "点踩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要点赞的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1是给question点赞，2是给answer，3是给comment", required = true, defaultValue = "1")
    })
    @RequestMapping(path = {"/unsupport"}, method = {RequestMethod.GET})
    public HashMap<String, String> unSupport(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        if(supportService.checkIsExist(id, type)){
            supportService.unSupport(id, type, hostHolder.getUser().getId());
        }
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    @ApiOperation(value = "取消点赞")
    @RequestMapping(path = {"/support_cancel"}, method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "点赞的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1是给question，2是给answer，3是给comment", required = true, defaultValue = "1")
    })
    public HashMap<String, String> cancelSupport(@RequestParam(required = true) int id, @RequestParam(required = true) int type) {
        if(supportService.checkIsExist(id, type)){
            String key = supportService.getKeyByType(type);
            if(key.equals("")){
                throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
            }
            key = key+"_SUPPORT_"+id;
            redisUtils.removeSetMember(key, String.valueOf(hostHolder.getUser().getId()));
        }
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }

    @ApiOperation(value = "取消点踩")
    @RequestMapping(path = {"/unsupport_cancel"}, method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "取消踩的id", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "type", value = "类型，1是给question，2是给answer，3是给comment", required = true, defaultValue = "1")
    })
    public HashMap<String, String> CancelUnSupport(@RequestParam(required = true) int id, @RequestParam(required = true) int type){
        if(supportService.checkIsExist(id, type)){
            String key = supportService.getKeyByType(type);
            if(key.equals("")){
                throw new ServiceException(MsgCodeEnum.PARAM_ERROR);
            }
            key = key+"_UNSUPPORT_"+id;
            redisUtils.removeSetMember(key, String.valueOf(hostHolder.getUser().getId()));
        }
        HashMap<String, String> result =new HashMap<>();
        result.put("msg", "SUCCESS");
        return result;
    }
}
