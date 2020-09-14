/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.api;

import org.apache.http.util.Asserts;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 用户api
 *
 * @author liwenqiang 2019-03-03 22:55
 **/
public interface HypervisorApi {

    /**
     * 根据传入的 username 查询信息
     *
     * @param username 用户账号
     * @return 如果查询到数据，返回查询到的信息，否则返回204状态码
     */
    default Mono<UserDetails> findByUsername(String username) {
        Asserts.notBlank(username, "username");
        return WebClient.create("http://abeille-basic-hypervisor").get().uri("/user/info/{username}", username)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(UserDetails.class);
    }
}
