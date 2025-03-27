package com.moujitx.myblog.server.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import com.moujitx.myblog.server.exception.ServiceException;

public class TokenUtils {

    private static final Integer EXPIRE_TIME_MINUTE = 60 * 2;

    private static final String KEY = "MOUJITX";

    public static String generateToken(String uuid) {
        try {
            return JWT.create()
                    .setPayload("uuid", uuid)
                    .setPayload(JWTPayload.ISSUED_AT, DateTime.now())
                    .setPayload(JWTPayload.NOT_BEFORE, DateTime.now())
                    .setPayload(JWTPayload.EXPIRES_AT,DateTime.now().offsetNew(DateField.MINUTE, EXPIRE_TIME_MINUTE))
                    .setKey(KEY.getBytes())
                    .sign();
        } catch (Exception e){
            throw new ServiceException("TOKEN生成失败");
        }
    }

    public static String getUUID(String token) {
        try {
            return JWT.of(token).getPayload("uuid").toString();
        } catch (Exception e){
            throw new ServiceException(401,"TOKEN解析失败");
        }
    }

    public static Boolean verifyToken(String token) {
        try {
            return JWT.of(token).setKey(KEY.getBytes()).validate(0);
        } catch (Exception e){
            throw new ServiceException(401,"TOKEN验证失败");
        }
    }
}

