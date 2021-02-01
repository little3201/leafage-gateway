package io.leafage.gateway.endpoint;

import org.springframework.http.ResponseCookie;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class CsrfTokenEndpoint {

    @GetMapping("/csrf")
    void csrfToken(ServerWebExchange exchange) {
        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
        csrfToken.doOnSuccess(token ->
                exchange.getResponse().addCookie(ResponseCookie.from(token.getHeaderName(), token.getToken())
                        .build())).then();
    }
}
