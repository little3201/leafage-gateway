package top.abeille.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import top.abeille.gateway.handler.AbeilleWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * webSocket 配置
 *
 * @author liwenqiang 2020/12/22 15:51
 */
@Configuration
public class ServerWebSocketConfiguration {

    @Bean
    public HandlerMapping handlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/socket", new AbeilleWebSocketHandler());

        return new SimpleUrlHandlerMapping(map, -1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
