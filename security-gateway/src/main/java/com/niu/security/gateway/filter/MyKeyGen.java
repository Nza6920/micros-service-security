package com.niu.security.gateway.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义 key 规则
 *
 * @version 1.0 [2021/08/01 21:35]
 * @author [nza]
 * @createTime [2021/08/01 21:35]
 */
@Component
public class MyKeyGen extends DefaultRateLimitKeyGenerator {
    public MyKeyGen(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        super(properties, rateLimitUtils);
    }

    @Override
    public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
        return super.key(request, route, policy);
    }
}
