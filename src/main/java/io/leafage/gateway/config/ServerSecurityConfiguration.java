/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package io.leafage.gateway.config;


import io.leafage.gateway.api.HypervisorApi;
import io.leafage.gateway.handler.ServerFailureHandler;
import io.leafage.gateway.handler.ServerSuccessHandler;
import io.leafage.gateway.service.JdbcReactiveUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

/**
 * spring security config .
 *
 * @author liwenqiang 2019/7/12 17:51
 */
@EnableWebFluxSecurity
public class ServerSecurityConfiguration {

    private final HypervisorApi hypervisorApi;

    public ServerSecurityConfiguration(HypervisorApi hypervisorApi) {
        this.hypervisorApi = hypervisorApi;
    }

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
     * 用户数据加载
     *
     * @return JdbcReactiveUserDetailsService 接口
     */
    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return new JdbcReactiveUserDetailsService(hypervisorApi);
    }

    /**
     * 安全配置
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.formLogin(f -> f.authenticationSuccessHandler(authenticationSuccessHandler())
                .authenticationFailureHandler(authenticationFailureHandler()))
                .logout(l -> l.logoutSuccessHandler(new HttpStatusReturningServerLogoutSuccessHandler()))
                .csrf(c -> c.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeExchange(a -> a.pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(HttpMethod.GET, "/assets/**", "/check").permitAll()
                        .pathMatchers(HttpMethod.PATCH, "/assets/posts/{schema}/like").permitAll()
                        .pathMatchers(HttpMethod.POST, "/register").permitAll()
                        .anyExchange().authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)));
        return http.build();
    }

    /**
     * 登陆成功后执行的处理器
     */
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new ServerSuccessHandler();
    }

    /**
     * 登陆失败后执行的处理器
     */
    private ServerAuthenticationFailureHandler authenticationFailureHandler() {
        return new ServerFailureHandler();
    }

}
