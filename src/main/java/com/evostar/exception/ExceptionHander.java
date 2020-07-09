package com.evostar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice    //"控制器增强"注解
public class ExceptionHander {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> errorHandler(Exception e) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msg", e.getMessage());//获取异常信息
        return result;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> errorHandler(ServiceException e) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", e.getCode());
        result.put("msg", e.getMsg());
        return result;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> UnauthorizedHandler(UnauthorizedException e) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msg", "未登录或登录已失效");
        return result;
    }

}
