package com.niu.security.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证 Filter
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 19:24]
 * @createTime [2021/08/01 19:24]
 */
@Component
@Slf4j
public class OauthFilter extends ZuulFilter {

    public static final String BEARER_PREFIX = "Bearer ";

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 过滤器类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行过滤
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("oauth start.....");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 判断是否是请求令牌
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }

        // 判断是否携带了 认证头
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isBlank(authHeader)) {
            return null;
        }

        // 判断请求头是否是 bearer 类型
        if (!StringUtils.startsWithIgnoreCase(authHeader, BEARER_PREFIX)) {
            return null;
        }


        try {
            TokenInfo info = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", info);
        } catch (Exception e) {
            log.error("get token info fail: ", e);
        }

        return null;
    }


    private TokenInfo getTokenInfo(String authHeader) {

        String token = StringUtils.substringAfter(authHeader, BEARER_PREFIX);
        String checkTokenUrl = "http://auth.niu.com:7046/oauth/check_token/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(checkTokenUrl, HttpMethod.POST, entity, TokenInfo.class);

        log.info("token info: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }
}
