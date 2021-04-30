package io.leafage.gateway.endpoint;

import io.leafage.gateway.api.HypervisorApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
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
     * @param exchange 请求体
     * @return 注册结果
     */
    @PostMapping("/register")
    Mono<Object> register(ServerWebExchange exchange) {
        return exchange.getFormData().map((data) -> {
            String email = data.getFirst("email");
            String password = data.getFirst("password");
            return hypervisorApi.createUser(email, password);
        });
    }

}
