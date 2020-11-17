/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.api;

import reactor.core.publisher.Mono;
import top.abeille.gateway.bo.UserBO;

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
    Mono<UserBO> findByUsername(String username);
}
