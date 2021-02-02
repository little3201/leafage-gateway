package io.leafage.gateway.endpoint;

import org.springframework.http.ResponseCookie;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Controller
public class CsrfTokenEndpoint {

    @GetMapping("/check")
    void csrfToken(ServerWebExchange exchange) {
        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
        if (Objects.nonNull(csrfToken)) {
            csrfToken.subscribe(token ->
                    exchange.getResponse().addCookie(ResponseCookie.from(token.getHeaderName(), token.getToken()).build()));
        }
    }
}
