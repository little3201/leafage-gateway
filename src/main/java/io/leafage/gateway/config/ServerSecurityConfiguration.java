/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package io.leafage.gateway.config;


import io.leafage.gateway.handler.ServerFailureHandler;
import io.leafage.gateway.handler.ServerSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;

/**
 * spring security config .
 *
 * @author liwenqiang 2019/7/12 17:51
 */
@EnableWebFluxSecurity
public class ServerSecurityConfiguration {

    /**
     * 密码配置，使用BCryptPasswordEncoder
     *
     * @return BCryptPasswordEncoder 加密方式
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全配置
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.formLogin(f -> f.authenticationSuccessHandler(new ServerSuccessHandler())
                .authenticationFailureHandler(new ServerFailureHandler()))
                .logout(l -> l.logoutSuccessHandler(new HttpStatusReturningServerLogoutSuccessHandler()))
                .authorizeExchange(a -> a.pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(HttpMethod.GET, "/assets/**", "/check", "/hypervisor/authority/tree").permitAll()
                        .pathMatchers(HttpMethod.PATCH, "/assets/posts/{schema}/like").permitAll()
                        .pathMatchers(HttpMethod.POST, "/register").permitAll()
                        .anyExchange().authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)));
        return http.build();
    }

}
