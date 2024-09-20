package com.example.mindkeep.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViewConfig {

    public static final String RESOURCE_NOT_FOUND = "errors/404";
    public static final String BAD_REQUEST = "errors/400";
    public static final String UNAUTHORIZED = "errors/401";
    public static final String INTERNAL_SERVER_ERROR = "errors/500";
    
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
