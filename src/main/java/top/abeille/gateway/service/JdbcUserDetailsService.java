/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */

package top.abeille.gateway.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;
import top.abeille.gateway.api.HypervisorApi;

import java.util.Set;
import java.util.stream.Collectors;

public class JdbcUserDetailsService implements ReactiveUserDetailsService {

    private final HypervisorApi hypervisorApi;

    public JdbcUserDetailsService(HypervisorApi hypervisorApi) {
        this.hypervisorApi = hypervisorApi;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return hypervisorApi.findByUsername(username).map(userBO ->
                User.builder()
                        .username(username)
                        .password(userBO.getPassword())
                        .accountExpired(!userBO.isAccountNonExpired())
                        .accountLocked(!userBO.isAccountNonLocked())
                        .credentialsExpired(!userBO.isCredentialsNonExpired())
                        .disabled(!userBO.isEnabled())
                        .authorities(grantedAuthorities(userBO.getAuthorities()))
                        .build());
    }

    private Set<GrantedAuthority> grantedAuthorities(Set<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

}
