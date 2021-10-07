package com.niu.security.login.domain.dto;

import lombok.Data;

/**
 * 刷新token
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 19:33]
 * @createTime [2021/08/01 19:33]
 */
@Data
public class RefreshTokenDto {

    private String refreshToken;
}
