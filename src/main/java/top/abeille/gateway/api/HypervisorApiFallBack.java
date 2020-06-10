package top.abeille.gateway.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.abeille.gateway.api.bo.UserBO;

/**
 * 用户api接口回调
 */
public class HypervisorApiFallBack implements HypervisorApi {

    @Override
    public Mono<UserBO> fetchUserByMobile(String mobile) {
        return null;
    }

    @Override
    public Mono<UserBO> fetchUserByEmail(String email) {
        return null;
    }

    @Override
    public Flux<String> retrieveRoles(String businessId) {
        return null;
    }
}
