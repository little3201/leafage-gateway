package top.abeille.gateway.handler;

import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * webSocket 处理器
 *
 * @author liwenqiang 2020/12/22 15:53
 */
public class AbeilleWebSocketHandler implements WebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(AbeilleWebSocketHandler.class);

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Mono<Void> input = session.receive()
                .doOnNext(message -> logger.info("receive message: {}", message))
                .concatMap(message -> {
                    logger.info("receive message: {}", message);
                    return Subscriber::onComplete;
                })
                .then();
        Flux<String> source = Flux.empty();
        Mono<Void> output = session.send(source.map(session::textMessage));
        return Mono.zip(input, output).then();
    }
}
