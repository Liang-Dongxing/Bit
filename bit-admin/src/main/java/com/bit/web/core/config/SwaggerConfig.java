package com.bit.web.core.config;

import com.bit.common.config.BitConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2的接口配置
 *
 * @author bit
 */
@Configuration
public class SwaggerConfig {
    /**
     * 系统基础配置
     */
    @Autowired
    private BitConfig BitConfig;

    @Value("${token.header}")
    private String header;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.info(apiInfo());
        Components components = new Components();
        /* 设置安全模式，swagger可以设置访问token */
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(header);
        components.addSecuritySchemes(header, securityScheme);
        openAPI.components(components);
        openAPI.addSecurityItem(new SecurityRequirement().addList(header));
        return openAPI;
    }

    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                // 扫描所有有注解的api，用这种方式更灵活
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Operation.class))
                .build();
    }

    /**
     * 添加摘要信息
     */
    private Info apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new Info()
                // 设置标题
                .title("标题：Bit管理系统_接口文档")
                // 描述
                .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                // 作者信息
                .contact(new Contact().name(BitConfig.getName()))
                // 版本
                .version("版本号:" + BitConfig.getVersion())
                .license(new License().name("MIT").url("https://github.com/Liang-Dongxing/Bit.git"));
    }
}
