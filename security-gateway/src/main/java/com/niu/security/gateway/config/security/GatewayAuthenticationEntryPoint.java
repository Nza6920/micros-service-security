package com.niu.security.gateway.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证异常处理器
 *
 * @author [nza]
 * @createTime [2022/01/03 21:10]
 */
@Component
@Slf4j
public class GatewayAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 没传令牌
        if (authException instanceof AccessTokenRequiredException) {
            log.error("未携带令牌, 请求: {}", request.getRequestURI());
        } else {
            // 令牌错误
            log.error("令牌错误, 请求: {}", request.getRequestURI());
        }
        super.commence(request, response, authException);
    }
}
