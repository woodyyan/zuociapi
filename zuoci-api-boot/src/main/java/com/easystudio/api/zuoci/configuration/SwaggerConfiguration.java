package com.easystudio.api.zuoci.configuration;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Value(value = "${info.version}")
    private String version;

    @Value(value = "${info.app.name}")
    private String name;

    @Value(value = "${info.app.customerDescription}")
    private String description;

    @Value(value = "${info.contact.email}")
    private String email;

    @Value(value = "${swagger.include.patterns}")
    private String includePatterns;

    @Value(value = "${info.app.base.url:https://}")
    private String protocol;

    @Bean
    public TypeResolver typeResolver() {
        return new TypeResolver();
    }

    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Customer v1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.easystudio.api.zuoci"))
                .build()
                .apiInfo(apiInfo())
                .protocols(extractProtocol());
    }

    private Set<String> extractProtocol() {
        return new HashSet<>(Collections.singletonList(protocol.split(":")[0]));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(name, description, version, "", new Contact("", "", email), "", "", Collections.emptyList());
    }
}