package io.leafage.gateway.api;

import io.leafage.gateway.bo.UserBO;
import io.leafage.gateway.bo.UserDetailsBO;
import org.apache.http.util.Asserts;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * hypervisor api .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
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
    public Mono<UserBO> createUser(String email, String password) {
        Asserts.notBlank(email, "email");
        Asserts.notBlank(password, "password");
        String username = email.substring(0, email.indexOf("@"));
        UserBO userBO = new UserBO(username, email, new BCryptPasswordEncoder().encode(password));
        return clientBuilder.build().post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userBO).retrieve().bodyToMono(UserBO.class);
    }

    @Override
    public Mono<Boolean> updatePassword(String username, String newPassword) {
        return null;
    }

}
