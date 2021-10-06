package com.niu.security.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 审计日志
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 19:43]
 * @createTime [2021/08/01 19:43]
 */
@Component
@Slf4j
public class AuditLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("audit log insert");
        return null;
    }
}
