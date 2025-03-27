package com.moujitx.myblog.server.common;

import cn.hutool.core.util.StrUtil;
import com.moujitx.myblog.server.exception.ServiceException;
import com.moujitx.myblog.server.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Function:token验证
 * Author: MOUJITX
 * Date: 2023/9/18 8:52
 */

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {


    @SuppressWarnings("null")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //跳过拦截器
        if (handler instanceof HandlerMethod){
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null){
                return true;
            }
        }

        String token = request.getHeader("token");  //header中
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");  //url中 ?token=***
        }

        if (StrUtil.isBlank(token)) {
            throw new ServiceException(401, "TOKEN为空");
        }

        if (TokenUtils.verifyToken(token)){
            return true;
        } else {
            throw new ServiceException(401, "TOKEN验证错误");
        }
    }
}
