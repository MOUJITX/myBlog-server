package com.moujitx.myblog.server.exception;

import com.moujitx.myblog.server.common.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Function: 自定义异常
 * Author: MOUJITX
 * Date: 2023/9/18 21:24
 */

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());
    }


    @SuppressWarnings("null")
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result serviceException(BindException e){
        return Result.errorWithTitle("数据新增或修改失败",e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public Result serviceException(IOException e){
        return Result.errorWithTitle("文件读写异常",e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Result serviceException(DataIntegrityViolationException e){
        System.out.println("error: "+e.getCause());
        String msg = e.getCause().getMessage();
        String content = "";
        if (msg.contains("Duplicate entry")) content = msg.split("\\.")[1].split("'")[0] + "重复";
        else if (msg.contains("Cannot delete or update a parent row: a foreign key constraint fails")) content = "其他表中存在值与被删除项绑定";
        else if (msg.contains("Cannot add or update a child row: a foreign key constraint fails")) content = "外键值不存在";
        else if (msg.contains("doesn't have a default value")) content = "存在必填项未填";
        else if (msg.contains("cannot be null")) content = "存在必填项未填";
        else {
            content = "数据库操作异常";
            System.out.println("error: "+e);
        }
        return Result.errorWithTitle("操作失败",content);
    }
}
