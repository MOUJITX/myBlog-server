package com.moujitx.myblog.server.exception;

import lombok.Getter;

/**
 * Function:
 * Author: MOUJITX
 * Date: 2023/9/18 21:26
 */
@Getter
public class ServiceException extends RuntimeException{

    private final Integer code;

    public ServiceException(String msg){
        super(msg);
        this.code = 500;
    }

    public ServiceException(Integer code, String msg){
        super(msg);
        this.code = code;
    }
}
