package com.niu.security.orderapi.server.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 配置资源服务器
 *
 * @author [nza]
 * @version 1.0 [2021/08/01 15:42]
 * @createTime [2021/08/01 15:42]
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String[] AUTH_LIST = new String[]{"/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**", "/actuator", "/actuator/health", "/actuator/health/**"};

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 配置当前资源服务的ID
        resources.resourceId(applicationName);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(AUTH_LIST)
                .permitAll()
                .antMatchers(HttpMethod.POST).access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.GET).access("#oauth2.hasScope('read')")
                .anyRequest()
                .authenticated();
    }
}
