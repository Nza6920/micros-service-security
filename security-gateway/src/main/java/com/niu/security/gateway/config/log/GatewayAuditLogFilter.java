package com.niu.security.gateway.config.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 日志过滤器
 * 不推荐作为 spring bean 注入, 会出现重复注入情况
 * 认证过滤器之后 授权过滤器之前
 *
 * @author [nza]
 * @createTime [2022/01/03 16:41]
 */
@Slf4j
public class GatewayAuditLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取当前用户
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[日志记录] 当前请求用户: {}", user);
        chain.doFilter(request, response);
        log.info("[日志记录] 成功");
    }
}
