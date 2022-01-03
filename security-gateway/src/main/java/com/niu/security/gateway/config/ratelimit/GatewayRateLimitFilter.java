package com.niu.security.gateway.config.ratelimit;

import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 限流过滤器
 *
 * @author [nza]
 * @createTime [2022/01/03 21:28]
 */
@Slf4j
public class GatewayRateLimitFilter extends OncePerRequestFilter {

    private final RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (rateLimiter.tryAcquire()) {
            chain.doFilter(request, response);
        } else {
            log.info("[限流日志] 执行限流处理, 请求地址: {}", request.getRequestURI());
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            Map<String, String> error = Maps.newHashMap();
            error.put("message", "too many request");
            response.getWriter().write(JSONUtil.toJsonStr(error));
            response.getWriter().flush();
        }
    }
}
