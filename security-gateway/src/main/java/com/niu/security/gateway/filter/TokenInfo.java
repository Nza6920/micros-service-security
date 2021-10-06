package com.niu.security.gateway.filter;

import lombok.Data;

import java.util.Date;

/**
 * Token 详情
 *
 * @version 1.0 [2021/08/01 19:33]
 * @author [nza]
 * @createTime [2021/08/01 19:33]
 */
@Data
public class TokenInfo {

    private boolean active;

    private String clientId;

    private String[] scope;

    private String user_name;

    private String[] aud;

    private Date exp;

    private String[] authorities;
}
