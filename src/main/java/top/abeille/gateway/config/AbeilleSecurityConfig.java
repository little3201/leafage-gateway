/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import top.abeille.gateway.api.HypervisorApi;
import top.abeille.gateway.handler.AbeilleFailureHandler;
import top.abeille.gateway.handler.AbeilleSuccessHandler;
import top.abeille.gateway.security.UserDetailsServiceImpl;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * spring security 配置
 *
 * @author liwenqiang 2019/7/12 17:51
 */
@EnableWebFluxSecurity
public class AbeilleSecurityConfig {

    private static final String KEY_STORE = "jwt/abeille-top-jwt.jks";
    private static final String KEY_PASS = "abeille-top";
    private static final String ALIAS = "abeille-top-jwt";

    @Autowired
    private HypervisorApi hypervisorApi;

    /**
     * 密码配置，使用BCryptPasswordEncoder
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(hypervisorApi);
    }

    /**
     * 安全配置
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.formLogin().authenticationSuccessHandler(authenticationSuccessHandler())
                .authenticationFailureHandler(authenticationFailureHandler())
                .and().csrf().disable()
                .authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and().exceptionHandling()
                .and().oauth2ResourceServer().jwt().jwtDecoder(jwtDecoder());
        return http.build();
    }

    /**
     * 登陆成功后执行的处理器
     */
    private ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AbeilleSuccessHandler();
    }

    /**
     * 登陆失败后执行的处理器
     */
    private ServerAuthenticationFailureHandler authenticationFailureHandler() {
        return new AbeilleFailureHandler();
    }

    /**
     * JWT 解码器
     */
    private ReactiveJwtDecoder jwtDecoder() {
        return new NimbusReactiveJwtDecoder(readRSAPublicKey());
    }

    /**
     * 获取RSAPublicKey
     */
    private RSAPublicKey readRSAPublicKey() {
        ClassPathResource ksFile = new ClassPathResource(KEY_STORE);
        KeyStoreKeyFactory ksFactory = new KeyStoreKeyFactory(ksFile, KEY_PASS.toCharArray());
        KeyPair keyPair = ksFactory.getKeyPair(ALIAS);
        return (RSAPublicKey) keyPair.getPublic();
    }
}
