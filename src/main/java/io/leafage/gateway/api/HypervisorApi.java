/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package io.leafage.gateway.api;

import io.leafage.gateway.bo.UserBO;
import io.leafage.gateway.bo.UserDetailsBO;
import reactor.core.publisher.Mono;

/**
 * hypervisor interface .
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
    Mono<UserDetailsBO> findByUsername(String username);

    /**
     * 用户注册
     *
     * @param email    邮箱
     * @param password 密码
     * @return 用户信息
     */
    Mono<UserBO> createUser(String email, String password);

    /**
     * 修改密码
     *
     * @param username    账号
     * @param newPassword 新密码
     * @return 操作结构
     */
    Mono<Boolean> updatePassword(String username, String newPassword);
}
