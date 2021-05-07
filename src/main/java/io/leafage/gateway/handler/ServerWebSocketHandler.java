package io.leafage.gateway.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * webSocket handler .
 *
 * @author liwenqiang 2020/12/22 15:53
 */
public class ServerWebSocketHandler implements WebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(ServerWebSocketHandler.class);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // 返回给client的内容
        Flux<WebSocketMessage> output = session.receive()
                .concatMap(mapper -> {
                    logger.info("receive message: {}", mapper.getPayloadAsText());
                    // 设置返回消息给client
                    return Flux.just(mapper.getPayloadAsText());
                })
                .map(session::textMessage);
        return session.send(output);
    }
}
