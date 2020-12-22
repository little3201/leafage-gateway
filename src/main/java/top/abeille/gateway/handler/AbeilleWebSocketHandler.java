package top.abeille.gateway.handler;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * webSocket 处理器
 *
 * @author liwenqiang 2020/12/22 15:53
 */
public class AbeilleWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session
                .send(session.receive()
                        .map(msg -> "RECEIVED ON SERVER :: " + msg.getPayloadAsText())
                        .map(session::textMessage)
                );
    }
}
