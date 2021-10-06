package com.niu.security.gateway.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

/**
 * 自定义错误处理
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 21:37]
 * @createTime [2021/08/01 21:37]
 */
@Component
public class MyRateLimitErrorHandler extends DefaultRateLimiterErrorHandler {

    @Override
    public void handleSaveError(String key, Exception e) {
        super.handleSaveError(key, e);
    }

    @Override
    public void handleFetchError(String key, Exception e) {
        super.handleFetchError(key, e);
    }

    @Override
    public void handleError(String msg, Exception e) {
        super.handleError(msg, e);
    }
}
