package com.moujitx.myblog.server.common;

import lombok.*;

/**
 * Function:消息统一返回接口
 * Author: MOUJITX
 * Date: 2023/9/17 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    public static final Integer CODE_SUCCESS = 200;
    public static final Integer CODE_AUTH_ERROR = 401;
    public static final Integer CODE_SYS_ERROR = 500;

    private Integer code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(CODE_SUCCESS, "Request Success", null);
    }

    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, "Request Success", data);
    }

    public static Result success(String title, Object data) {
        return new Result(CODE_SUCCESS, title, data);
    }

    public static Result error(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result error(String msg) {
        return new Result(CODE_SYS_ERROR, msg, null);
    }

    public static Result error(Object data) {
        return new Result(CODE_SYS_ERROR, "error", data);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result errorWithTitle(String title, String msg) {
        return new Result(CODE_SYS_ERROR, title, msg);
    }

    public static Result error() {
        return new Result(CODE_SYS_ERROR, "System Error", null);
    }
}
