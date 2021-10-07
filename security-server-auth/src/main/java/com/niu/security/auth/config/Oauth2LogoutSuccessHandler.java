package com.niu.security.auth.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理器
 *
 * @author [nza]
 * @createTime [2021/10/07 12:12]
 */
@Component
public class Oauth2LogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // 退出成功之后跳转到指定路径
        String redirectUri = request.getParameter("redirect_uri");
        if (!StringUtils.isEmpty(redirectUri)) {
            response.sendRedirect(redirectUri);
        }
    }
}
