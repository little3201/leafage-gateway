package io.leafage.gateway.endpoint;

import io.leafage.gateway.api.HypervisorApi;
import io.leafage.gateway.bo.UserBO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * endpoint for register api .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
@RestController
public class ServerEndpoint {

    private final HypervisorApi hypervisorApi;
    private final ReactiveUserDetailsPasswordService reactiveUserDetailsPasswordService;

    public ServerEndpoint(HypervisorApi hypervisorApi, ReactiveUserDetailsPasswordService reactiveUserDetailsPasswordService) {
        this.hypervisorApi = hypervisorApi;
        this.reactiveUserDetailsPasswordService = reactiveUserDetailsPasswordService;
    }

    /**
     * 用户注册
     *
     * @param exchange 请求体
     * @return 注册结果
     */
    @PostMapping("/register")
    public Mono<UserBO> register(ServerWebExchange exchange) {
        return exchange.getFormData().flatMap(data -> {
            String email = data.getFirst("email");
            String password = data.getFirst("password");
            return hypervisorApi.createUser(email, password);
        });
    }

    /**
     * 修改密码
     *
     * @param authentication 认证信息
     * @param newPassword    新密码
     * @return 修改结果
     */
    @PostMapping("/password")
    public Mono<UserDetails> updatePassword(Authentication authentication, String newPassword) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reactiveUserDetailsPasswordService.updatePassword(userDetails, newPassword);
    }

}
