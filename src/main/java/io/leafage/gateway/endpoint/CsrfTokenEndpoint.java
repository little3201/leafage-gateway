package io.leafage.gateway.endpoint;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Objects;

/**
 * endpoint for csrf
 *
 * @author liwenqiang 2019-03-03 22:55
 */
@Controller
public class CsrfTokenEndpoint {

    @GetMapping("/check")
    public Mono<Void> csrfToken(ServerWebExchange exchange) {
        Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
        if (Objects.nonNull(csrfToken)) {
            return csrfToken.doOnSuccess(csrf -> exchange.getResponse()).then();
        }
        return Mono.empty();
    }
}
