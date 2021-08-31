package io.leafage.gateway.service;

import io.leafage.gateway.api.HypervisorApi;
import io.leafage.gateway.bo.UserDetailsBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Collections;
import static org.mockito.BDDMockito.given;

/**
 * JDBC操作用户测试类
 *
 * @author liwenqiang 2021/8/30 17:04
 */
@ExtendWith(MockitoExtension.class)
class JdbcReactiveUserDetailsServiceTest {

    @Mock
    private HypervisorApi hypervisorApi;

    @InjectMocks
    private JdbcReactiveUserDetailsService jdbcReactiveUserDetailsService;

    @Test
    void findByUsername() {
        UserDetailsBO userDetailsBO = new UserDetailsBO();
        userDetailsBO.setUsername("little3201");
        userDetailsBO.setPassword("123456");
        userDetailsBO.setAuthorities(Collections.singleton("test"));
        userDetailsBO.setEnabled(true);
        userDetailsBO.setAccountNonExpired(true);
        userDetailsBO.setCredentialsNonExpired(true);
        given(this.hypervisorApi.findByUsername(Mockito.anyString())).willReturn(Mono.just(userDetailsBO));

        StepVerifier.create(jdbcReactiveUserDetailsService.findByUsername("little3201")).expectNextCount(1).verifyComplete();
    }

}