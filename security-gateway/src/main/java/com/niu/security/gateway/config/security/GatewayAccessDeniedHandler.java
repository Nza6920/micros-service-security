package com.niu.security.gateway.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义无权限处理器
 *
 * @author [nza]
 * @createTime [2022/01/03 21:03]
 */
@Component
@Slf4j
public class GatewayAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        log.error("无权限访问, 请求地址: {}", request.getRequestURI());
        super.handle(request, response, authException);
    }
}
