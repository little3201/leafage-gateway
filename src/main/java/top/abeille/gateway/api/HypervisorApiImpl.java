/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */

package top.abeille.gateway.api;


import org.apache.http.util.Asserts;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HypervisorApiImpl implements HypervisorApi {

    private final WebClient.Builder clientBuilder;

    public HypervisorApiImpl(WebClient.Builder clientBuilder) {
        clientBuilder.baseUrl("http://abeille-basic-hypervisor").build();
        this.clientBuilder = clientBuilder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Asserts.notBlank(username, "username");
        return clientBuilder.build().get().uri("/user/info/{username}", username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(UserDetails.class);
    }
}
