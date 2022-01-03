package com.niu.security.gateway.service.impl;

import cn.hutool.json.JSONUtil;
import com.niu.security.gateway.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限
 *
 * @author [nza]
 * @createTime [2021/10/12 23:09]
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 不处理匿名请求
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessTokenRequiredException(null);
        }
        log.info("[权限校验] 请求URL: {}, authentication: {}", request.getRequestURI(), JSONUtil.toJsonStr(authentication));
        return RandomUtils.nextInt() % 2 == 0;
    }
}
