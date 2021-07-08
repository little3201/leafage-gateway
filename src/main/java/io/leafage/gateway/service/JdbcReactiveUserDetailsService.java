/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */

package io.leafage.gateway.service;

import io.leafage.gateway.api.HypervisorApi;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * user details service .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
@Service
public class JdbcReactiveUserDetailsService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private final HypervisorApi hypervisorApi;

    public JdbcReactiveUserDetailsService(HypervisorApi hypervisorApi) {
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

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return Mono.just(user)
                .map(userDetails -> withNewPassword(userDetails, newPassword))
                .map(userDetails -> {
                    this.hypervisorApi.updatePassword(userDetails.getUsername(), userDetails.getPassword());
                    return userDetails;
                });
    }

    private UserDetails withNewPassword(UserDetails userDetails, String newPassword) {
        return User.withUserDetails(userDetails)
                .password(newPassword)
                .build();
    }

}
