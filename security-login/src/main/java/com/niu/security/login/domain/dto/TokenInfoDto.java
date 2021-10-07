package com.niu.security.login.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Token 详情
 *
 * @version 1.0 [2021/08/01 19:33]
 * @author [nza]
 * @createTime [2021/08/01 19:33]
 */
@Data
public class TokenInfoDto {

    private String access_token;

    private String refresh_token;

    private String token_type;

    private Long expires_in;

    private String scope;

    private LocalDateTime expireTime;

    public TokenInfoDto initExpireTime() {
        expireTime = LocalDateTime.now().plusSeconds(expires_in - 3);
        return this;
    }

    public boolean isExpired() {
        if (expireTime == null) {
            return false;
        }
        return expireTime.isBefore(LocalDateTime.now());
    }
}
