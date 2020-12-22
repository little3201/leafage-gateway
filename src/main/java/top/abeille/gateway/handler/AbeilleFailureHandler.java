package top.abeille.gateway.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AbeilleFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(exception.getMessage().getBytes(UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

}
