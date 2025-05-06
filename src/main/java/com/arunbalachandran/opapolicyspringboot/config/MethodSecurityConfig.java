package com.arunbalachandran.opapolicyspringboot.config;

import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// prePostEnabled = false to support custom preauthorize method
@Configuration
@EnableMethodSecurity(prePostEnabled = false)
public class MethodSecurityConfig {
    @Bean
    public Advisor preAuthorize(CustomAuthorizationManager manager) {
        return AuthorizationManagerBeforeMethodInterceptor.preAuthorize(manager);
    }
}
