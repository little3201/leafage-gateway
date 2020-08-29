/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关过滤配置
 *
 * @author liwenqiang 2019-10-29
 */
public class AbeilleFilter implements GlobalFilter {

    private final Logger log = LoggerFactory.getLogger(AbeilleFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("请求路径：{}", path);
        return chain.filter(exchange);
    }
}
