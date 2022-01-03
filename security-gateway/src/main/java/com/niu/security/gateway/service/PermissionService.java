package com.niu.security.gateway.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限服务
 *
 * @author [nza]
 * @createTime [2021/10/12 23:07]
 */
public interface PermissionService {

    /**
     * 是否有权限
     *
     * @param request        {@link HttpServletRequest}
     * @param authentication {@link Authentication}
     * @return true 有 false 无
     * @author [nza]
     * @createTime 2021/10/12 23:08
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
