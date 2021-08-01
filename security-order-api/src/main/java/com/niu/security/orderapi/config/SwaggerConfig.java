package com.niu.security.orderapi.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger 配置
 *
 * @author [nza]
 * @version 1.0 [2021/05/27 22:36]
 * @createTime [2021/05/27 22:36]
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * swagger 配置类对象
     *
     * @return {@link Docket}
     * @author nza
     * @createTime 2021/5/27 22:37
     */
    @Bean
    public Docket createRestApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        ApiInfoBuilder builder = new ApiInfoBuilder();

        // 设置 swagger 标题
        builder.title("ORDER API");
        ApiInfo apiInfo = builder.build();
        docket.apiInfo(apiInfo);

        // 设置那些类出现在 swagger 里面
        ApiSelectorBuilder selectorBuilder = docket.select();
        // 类路径
        selectorBuilder.paths(PathSelectors.any());
        // 注解
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        docket = selectorBuilder.build();

        // 配置支持 jwt
        ApiKey apiKey = new ApiKey("authorization", "Authorization", "header");
        List<ApiKey> apiKeyList = Lists.newArrayList(apiKey);
        docket.securitySchemes(apiKeyList);

        // 配置令牌作用域
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[]{scope};
        SecurityReference reference = new SecurityReference("authorization", scopes);
        List<SecurityReference> referenceList = Lists.newArrayList(reference);
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(referenceList)
                .build();
        List<SecurityContext> securityContextList = Lists.newArrayList(securityContext);
        docket.securityContexts(securityContextList);

        return docket;
    }
}
