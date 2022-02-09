/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */

package io.leafage.gateway.service;

import io.leafage.gateway.domain.document.Account;
import io.leafage.gateway.repository.AccountRepository;
import io.leafage.gateway.repository.AccountRoleRepository;
import io.leafage.gateway.repository.RoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * user details service .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
@Service
public class ServerReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final RoleRepository roleRepository;

    public ServerReactiveUserDetailsService(AccountRepository accountRepository, AccountRoleRepository accountRoleRepository,
                                            RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<Account> accountMono = accountRepository.getByUsername(username)
                .switchIfEmpty(Mono.error(() -> new NoSuchElementException("User Not Found")));

        Mono<Set<GrantedAuthority>> authoritiesMono = accountMono.map(account ->
                        accountRoleRepository.findByAccountIdAndEnabledTrue(account.getId())
                                .flatMap(accountRole -> roleRepository.findById(accountRole.getRoleId())))
                .flatMap(roleFlux -> roleFlux.map(role -> new SimpleGrantedAuthority(role.getCode()))
                        .collect(HashSet::new, HashSet::add));

        // 构造用户信息
        return accountMono.zipWith(authoritiesMono, (account, authorities) -> {
            LocalDateTime now = LocalDateTime.now();
            boolean isAccountNonExpired = account.getAccountExpiresAt().isAfter(now);
            boolean isCredentialsNonExpired = account.getCredentialsExpiresAt().isAfter(now);
            return new User(account.getUsername(), account.getPassword(), account.isEnabled(), isAccountNonExpired,
                    isCredentialsNonExpired, !account.isAccountLocked(), authorities);
        });
    }

}
