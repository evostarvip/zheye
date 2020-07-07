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

    @ExceptionHandler(Exception.class)//用于注释异常处理类，value属性指定需要拦截的异常类型
    @ResponseBody //和controller方法上的用法一样，会将方法中的返回值转json后返回给客户端
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> errorHandler(Exception e) {        //捕获异常并获取异常处理对象
        Map<String, Object> result = new HashMap<String, Object>();
        //result.put("code", "500");
        result.put("msg", e.getMessage());//获取异常信息
        return result;
    }

    @ExceptionHandler(MyException.class)    //在这里将value属性改为自定义的异常类，表示将拦截MyException异常
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> errorHandler(MyException e) {
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
