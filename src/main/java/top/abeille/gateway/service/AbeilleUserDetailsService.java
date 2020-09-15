/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */

package top.abeille.gateway.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;
import top.abeille.gateway.api.HypervisorApi;

public class AbeilleUserDetailsService implements ReactiveUserDetailsService {

    private final HypervisorApi hypervisorApi;

    public AbeilleUserDetailsService(HypervisorApi hypervisorApi) {
        this.hypervisorApi = hypervisorApi;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return hypervisorApi.findByUsername(username);
    }

}
