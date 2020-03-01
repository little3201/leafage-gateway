/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * oauth2 resource server common
 *
 * @author liwenqiang 2018/12/31 14:42
 **/
@Configuration
class ResourceServerConfig {

    /**
     * 定义OAuth2请求匹配器
     */
    private static class Oauth2RequestedMatcher implements ServerWebExchangeMatcher {
        @Override
        public Mono<MatchResult> matches(ServerWebExchange serverWebExchange) {
            // 判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头,
            String auth = serverWebExchange.getAttribute(HttpHeaders.AUTHORIZATION);
            boolean haveOauth2Token = StringUtils.isNotBlank(auth) && auth.startsWith(OAuth2AccessToken.TokenType.BEARER.getValue());
            // 或者是请求参数中包含access_token参数,满足其中一个则匹配成功
            String accessToken = serverWebExchange.getAttribute("access_token");
            boolean haveAccessToken = StringUtils.isNotBlank(accessToken);
            if (haveOauth2Token || haveAccessToken) {
                return MatchResult.match();
            }
            return MatchResult.notMatch();
        }
    }
}
