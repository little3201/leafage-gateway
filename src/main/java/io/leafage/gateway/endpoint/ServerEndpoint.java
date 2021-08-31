package io.leafage.gateway.endpoint;

import io.leafage.gateway.api.HypervisorApi;
import io.leafage.gateway.bo.UserBO;
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

    public ServerEndpoint(HypervisorApi hypervisorApi) {
        this.hypervisorApi = hypervisorApi;
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

}
