package com.evostar.exception;

import com.evostar.model.ErrorCode;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private int code;
    private String msg;

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMessage();
    }

    public ServiceException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
