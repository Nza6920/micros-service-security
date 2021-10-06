package com.niu.security.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token controller
 *
 * @author [nza]
 * @createTime [2021/10/06 14:48]
 */
@RestController
@RequestMapping("/oauth")
public class TokenController {

    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private ConsumerTokenServices tokenService;

    @PostMapping("/revokeToken")
    public void revokeToken(@RequestHeader("Authorization") String token) {
        if (StringUtils.startsWithIgnoreCase(token, BEARER_PREFIX)) {
            tokenService.revokeToken(StringUtils.substringAfter(token, BEARER_PREFIX));
        }
    }
}
