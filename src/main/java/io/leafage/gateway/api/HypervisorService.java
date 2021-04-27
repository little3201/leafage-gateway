package io.leafage.gateway.api;

import io.leafage.gateway.bo.UserBO;
import io.leafage.gateway.bo.UserDetailsBO;
import org.apache.http.util.Asserts;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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
    public Mono<UserDetailsBO> findByUsername(String username) {
        Asserts.notBlank(username, "username");
        return clientBuilder.build().get().uri("/user/{username}/details", username)
                .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(UserDetailsBO.class);
    }

    @Override
    public Mono<UserBO> createUser(String username, String email, String password) {
        Asserts.notBlank(username, "username");
        Asserts.notBlank(email, "email");
        Asserts.notBlank(password, "password");
        UserBO userBO = new UserBO(username, email, password);
        return clientBuilder.build().post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userBO).retrieve().bodyToMono(UserBO.class);
    }
}
