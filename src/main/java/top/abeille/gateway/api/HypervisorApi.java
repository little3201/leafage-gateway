/*
 * Copyright (c) 2019. Abeille All Right Reserved.
 */
package top.abeille.gateway.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import top.abeille.gateway.api.bo.UserBO;

import java.util.ArrayList;

/**
 * 用户api
 *
 * @author liwenqiang 2019-03-03 22:55
 **/
@FeignClient(name = "abeille-basic-hypervisor", fallback = HypervisorApiFallBack.class)
public interface HypervisorApi {

    /**
     * 根据传入的手机号: mobile 查询信息
     *
     * @param mobile 手机号
     * @return 如果查询到数据，返回查询到的信息，否则返回404状态码
     */
    @GetMapping("/user/mobile/{mobile}")
    Mono<UserBO> fetchUserByMobile(@RequestParam String mobile);

    /**
     * 根据传入的邮箱: email 查询信息
     *
     * @param email 邮箱
     * @return 如果查询到数据，返回查询到的信息，否则返回404状态码
     */
    @GetMapping("/user/email/{email}")
    Mono<UserBO> fetchUserByEmail(@RequestParam String email);

    /**
     * 根据用户业务ID查询角色信息
     *
     * @param businessId 用户业务ID
     * @return 角色业务ID集合
     */
    @GetMapping("/role/relation/{businessId}")
    Mono<ArrayList<String>> retrieveRoles(@RequestParam String businessId);
}
