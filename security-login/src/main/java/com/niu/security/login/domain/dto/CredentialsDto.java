package com.niu.security.login.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户令牌
 *
 * @author [nza]
 * @createTime [2021/09/21 20:49]
 */
@Data
public class CredentialsDto {

    @NotEmpty(message = "username 不能为空")
    private String username;

    @NotEmpty(message = "password 不能为空")
    private String password;
}
