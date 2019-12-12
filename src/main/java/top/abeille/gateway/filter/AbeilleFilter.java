/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关过滤配置
 *
 * @author liwenqiang 2019-10-29
 */
public class AbeilleFilter implements GlobalFilter {

    private static final String ACCESS_TOKEN = "access_token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String param = exchange.getRequest().getQueryParams().getFirst(ACCESS_TOKEN);
        if (StringUtils.isBlank(param)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
