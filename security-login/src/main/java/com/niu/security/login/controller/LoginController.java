package com.niu.security.login.controller;

import com.niu.security.login.domain.dto.CredentialsDto;
import com.niu.security.login.domain.dto.TokenInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    private static final String loginUrl = "http://gateway.niu.com:7056/token/oauth/token";

    private static final String logoutUrl = "http://gateway.niu.com:7056/token/oauth/revokeToken";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public TokenInfoDto login(@RequestBody @Validated CredentialsDto credentials) {
        return getTokenInfo(credentials);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authHeader, HttpServletRequest request) {
        doLogout(authHeader, request);
    }


    /**
     * 退出登录
     *
     * @param authHeader authorization header
     * @author [nza]
     * @createTime 2021/10/6 14:31
     */
    private void doLogout(String authHeader, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, Object.class);

        request.getSession().invalidate();

        log.info("token logout: {}", responseEntity.getBody());
    }

    /**
     * 获取 token
     *
     * @param credentialsDto {@link CredentialsDto}
     * @return {@link TokenInfoDto}
     * @author [nza]
     * @createTime 2021/9/21 20:59
     */
    private TokenInfoDto getTokenInfo(CredentialsDto credentialsDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBasicAuth("adminService", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", credentialsDto.getUsername());
        params.add("password", credentialsDto.getPassword());
        params.add("grant_type", "password");
        params.add("scope", "read write");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfoDto> responseEntity = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, TokenInfoDto.class);

        log.info("token info: {}", responseEntity.getBody());

        return responseEntity.getBody();
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

        ResponseEntity<TokenInfoDto> responseEntity = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, TokenInfoDto.class);

        log.info("token info: {}", responseEntity.getBody());
        request.getSession().setAttribute("token", responseEntity.getBody());

        // 注意同源,否则 session 会丢失
        response.sendRedirect("http://admin.niu.com:17016/");
    }

    @GetMapping("/me")
    public TokenInfoDto me(HttpServletRequest request) {
        Object token = request.getSession().getAttribute("token");
        return (TokenInfoDto) token;
    }
}
