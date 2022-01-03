package com.niu.security.gateway.config.security;

import com.niu.security.gateway.config.ratelimit.GatewayRateLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * GatewaySecurityConfig
 *
 * @author [nza]
 * @createTime [2021/10/10 19:43]
 */
@Configuration
@EnableResourceServer
public class GatewaySecurityConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private GatewayWebSecurityExpressionHandler gatewayWebSecurityExpressionHandler;

    @Autowired
    private GatewayAccessDeniedHandler gatewayAccessDeniedHandler;

    @Autowired
    private GatewayAuthenticationEntryPoint gatewayAuthenticationEntryPoint;

    //    @Override
    //    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    //        // 配置当前服务的 resource id
    //        resources.resourceId()
    //    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 添加限流过滤器到 spring security 过滤器链第一个过滤器之前
        http.addFilterBefore(new GatewayRateLimitFilter(), SecurityContextPersistenceFilter.class)
                // 添加日志过滤器到 spring security 异常处理器之后
//                .addFilterAfter(new GatewayAuditLogFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests()
                // token 相关的请求不需要认证
                .antMatchers("/token/**").permitAll()
                .anyRequest()
                .access("#permissionService.hasPermission(request, authentication)");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 配置自定义表达式处理器
        resources.expressionHandler(gatewayWebSecurityExpressionHandler)
                // 设置无权限响应处理器 403
                .accessDeniedHandler(gatewayAccessDeniedHandler)
                // 设置认证失败处理器
                .authenticationEntryPoint(gatewayAuthenticationEntryPoint);
    }
}
