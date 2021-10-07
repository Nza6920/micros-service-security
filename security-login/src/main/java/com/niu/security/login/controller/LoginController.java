package com.niu.security.login.controller;

import com.niu.security.login.domain.dto.RefreshTokenDto;
import com.niu.security.login.domain.dto.TokenInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    private static final String tokenUrl = "http://gateway.niu.com:7056/token/oauth/token";

    private static final String revokeTokenUrl = "http://gateway.niu.com:7056/token/oauth/revokeToken";

    private static final String refreshTokenUrl = "http://gateway.niu.com:7056/token/oauth/token";

    private static final String TOKEN = "token";

    @Autowired
    private RestTemplate restTemplate;

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

        // 失效 token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(revokeTokenUrl, HttpMethod.POST, entity, Object.class);

        // 失效前端服务器的 session
        request.getSession().invalidate();

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
        }
        log.info("token info: {}", token);
        request.getSession().setAttribute(TOKEN, token);

        // 注意同源,否则 session 会丢失
        response.sendRedirect("http://admin.niu.com:17016/");
    }

    @GetMapping("/me")
    public TokenInfoDto me(HttpServletRequest request) {
        Object token = request.getSession().getAttribute(TOKEN);
        return (TokenInfoDto) token;
    }

    /**
     * 刷新token
     */
    @PostMapping("/refreshToken")
    public TokenInfoDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletRequest request) {
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
        }

        request.getSession().setAttribute(TOKEN, body);

        return body;
    }
}
