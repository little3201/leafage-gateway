package io.leafage.gateway.endpoint;

import io.leafage.gateway.api.HypervisorApi;
import io.leafage.gateway.bo.UserBO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RegisterEndpoint {

    private final HypervisorApi hypervisorApi;

    public RegisterEndpoint(HypervisorApi hypervisorApi) {
        this.hypervisorApi = hypervisorApi;
    }

    /**
     * 用户注册
     *
     * @param email    邮箱
     * @param password 密码
     * @return 注册结果
     */
    @PostMapping("/register")
    Mono<UserBO> register(String email, String password) {
        return hypervisorApi.createUser(email, password);
    }
}
