package io.leafage.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * security filter .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
public class ServerSecurityFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ServerSecurityFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("ServerSecurityFilter");
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
