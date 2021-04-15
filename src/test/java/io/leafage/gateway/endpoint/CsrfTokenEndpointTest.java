package io.leafage.gateway.endpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CsrfTokenEndpoint.class)
class CsrfTokenEndpointTest {

    @Autowired
    private WebTestClient webTestClient;

    @WithMockUser
    @Test
    void csrfToken() {
        webTestClient.get().uri("/check").exchange().expectCookie().exists("_csrf");
    }
}