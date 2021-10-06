package com.niu.security.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权过滤器
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 19:44]
 * @createTime [2021/08/01 19:44]
 */
@Component
@Slf4j
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("authorization start......");

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        // 判断是否需要认证
        if (isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");

            // 判断 token 是否合法
            if (tokenInfo != null && tokenInfo.isActive()) {
                // 判断是否拥有权限
                if (!hasPermission(tokenInfo, request)) {
                    log.error("audit log update fail 403");
                    handleError(HttpStatus.FORBIDDEN.value(), currentContext);
                }
                currentContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
            } else {
                if (!StringUtils.startsWith(request.getRequestURI(), "/token")) {
                    log.error("audit log update fail 401");
                    handleError(HttpStatus.UNAUTHORIZED.value(), currentContext);
                }
            }
        }

        return null;
    }

    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
//        return RandomUtils.nextInt() % 2 == 0;
        return true;
    }

    private void handleError(int status, RequestContext currentContext) {

        currentContext.getResponse().setContentType(ContentType.APPLICATION_JSON.getMimeType());
        currentContext.setResponseStatusCode(status);
        currentContext.setResponseBody("{\"message\": \"auth fail\"}");

        // 告诉 zuul 不继续向下传递请求
        currentContext.setSendZuulResponse(false);
    }

    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }
}
