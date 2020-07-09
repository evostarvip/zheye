package com.evostar.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException{
    private int code;
    private String msg;

    public ServiceException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
