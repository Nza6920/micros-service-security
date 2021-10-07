package com.niu.security.login.controller;

import cn.hutool.core.util.StrUtil;
import com.niu.security.login.domain.dto.RefreshTokenDto;
import com.niu.security.login.domain.dto.TokenInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * test controller
 *
 * @author [nza]
 * @createTime [2021/08/31 22:54]
 */
@RestController
@RequestMapping("/users")
@Slf4j
public class LoginController {

    private static final String tokenUrl = "http://gateway.niu.com:7056/token/oauth/token";

    private static final String revokeTokenUrl = "http://gateway.niu.com:7056/token/oauth/revokeToken";

    private static final String refreshTokenUrl = "http://gateway.niu.com:7056/token/oauth/token";

    private static final String ACCESS_TOKEN = "access_token";

    private static final String REFRESH_TOKEN = "refresh_token";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authHeader, HttpServletRequest request, HttpServletResponse response) {
        doLogout(authHeader, request, response);
    }

    /**
     * 退出登录
     *
     * @param authHeader authorization header
     * @author [nza]
     * @createTime 2021/10/6 14:31
     */
    private void doLogout(String authHeader, HttpServletRequest request, HttpServletResponse response) {

        // 失效 token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(revokeTokenUrl, HttpMethod.POST, entity, Object.class);

        log.info("token logout: {}", responseEntity.getBody());
    }

    @GetMapping("/callback")
    public void callback(@RequestParam String code, @RequestParam String state, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("state: {}", state);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth("adminService", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://admin.niu.com:17016/login-bff/users/callback");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfoDto> responseEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, TokenInfoDto.class);

        TokenInfoDto token = responseEntity.getBody();
        if (token != null) {

            token.initExpireTime();

            optionCookie(response, token);
            response.sendRedirect("http://admin.niu.com:17016/");
        }

        log.info("token info: {}", token);
    }

    @GetMapping("/me")
    public TokenInfoDto me(HttpServletRequest request) {

        String access_token = getCookie(ACCESS_TOKEN, request);
        String refresh_token = getCookie(REFRESH_TOKEN, request);
        if (StrUtil.isEmpty(access_token)) {
            return null;
        }

        TokenInfoDto tokenInfoDto = new TokenInfoDto();
        tokenInfoDto.setAccess_token(access_token);
        tokenInfoDto.setRefresh_token(refresh_token);
        return tokenInfoDto;
    }

    private String getCookie(String name, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (StrUtil.equals(cookie.getName(), name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 刷新token
     */
    @PostMapping("/refreshToken")
    public TokenInfoDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("adminService", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshTokenDto.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfoDto> responseEntity = restTemplate.exchange(refreshTokenUrl, HttpMethod.POST, entity, TokenInfoDto.class);
        TokenInfoDto body = responseEntity.getBody();
        if (body != null) {
            body.initExpireTime();
            optionCookie(response, body);
        }
        return body;
    }

    /**
     * <p>删除无效cookie</p>
     * <p>无效☞1.过时 2.未发布</p>
     *
     * @param request   请求
     * @param response  响应
     * @param deleteKey 需要删除cookie的名称
     */
    private void deleteCookieByName(HttpServletRequest request, HttpServletResponse response, String deleteKey) {
        for (Cookie item : request.getCookies()) {
            if (StrUtil.equals(item.getName(), deleteKey)) {
                Cookie cookie = new Cookie(deleteKey, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
    }

    private void optionCookie(HttpServletResponse response, TokenInfoDto body) {
        Cookie tokenCookie = new Cookie(ACCESS_TOKEN, body.getAccess_token());
        tokenCookie.setMaxAge(body.getExpires_in().intValue() - 3);
        tokenCookie.setDomain("niu.com");
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);

        Cookie refreshCookie = new Cookie(REFRESH_TOKEN, body.getRefresh_token());
        refreshCookie.setMaxAge(259200);
        refreshCookie.setDomain("niu.com");
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
    }
}
