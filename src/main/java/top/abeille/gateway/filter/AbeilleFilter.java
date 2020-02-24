/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.filter;

/**
 * 跨域配置
 *
 * @author liwenqiang 2019-06-12
 */
public class AbeilleFilter {
//public class AbeilleFilter implements GlobalFilter {


    /*@Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String accessToken = exchange.getRequest().getQueryParams().getFirst("access_token");
        if(StringUtils.isBlank(accessToken)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }*/
}