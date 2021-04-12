package io.leafage.gateway.api;

import io.leafage.gateway.bo.UserBO;
import org.apache.http.util.Asserts;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HypervisorService implements HypervisorApi {

    private final WebClient.Builder clientBuilder;

    public HypervisorService(WebClient.Builder clientBuilder) {
        clientBuilder.baseUrl("http://hypervisor").build();
        this.clientBuilder = clientBuilder;
    }

    @Override
    public Mono<UserBO> findByUsername(String username) {
        Asserts.notBlank(username, "username");
        return clientBuilder.build().get().uri("/user/{username}/details", username)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UserBO.class);
    }

    @Override
    public Mono<UserBO> createUser(String username, String email, String password) {
        Asserts.notBlank(username, "username");
        Asserts.notBlank(email, "email");
        Asserts.notBlank(password, "password");
        // 构造map
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", username);
        multiValueMap.add("email", email);
        multiValueMap.add("password", password);
        return clientBuilder.build().post().uri("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(multiValueMap))
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UserBO.class);
    }
}
