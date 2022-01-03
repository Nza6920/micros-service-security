package com.niu.security.gateway.config.security;

import com.niu.security.gateway.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * 权限表达式处理器
 *
 * @author [nza]
 * @createTime [2021/10/12 23:17]
 */
@Component
public class GatewayWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Autowired
    private PermissionService permissionService;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        // 设置评估上下文
        StandardEvaluationContext evaluationContextInternal = super.createEvaluationContextInternal(authentication, invocation);
        evaluationContextInternal.setVariable("permissionService", permissionService);
        return evaluationContextInternal;
    }
}
