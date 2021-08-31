package io.leafage.gateway.endpoint;

import io.leafage.gateway.api.HypervisorApi;
import io.leafage.gateway.bo.UserBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

/**
 * 服务接口测试类
 *
 * @author liwenqiang 2021/8/30 17:04
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ServerEndpoint.class)
class ServerEndpointTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private HypervisorApi hypervisorApi;

    @Test
    @WithMockUser
    void register() {
        UserBO userBO = new UserBO("little3201", "test@leafage.top", "123456");
        given(this.hypervisorApi.createUser(Mockito.anyString(), Mockito.anyString())).willReturn(Mono.just(userBO));

        webTestClient.mutateWith(csrf()).post().uri("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("email", "test@leafage.top").with("password", "123456"))
                .exchange().expectStatus().isOk();
    }
}