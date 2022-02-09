package io.leafage.gateway.service;

import io.leafage.gateway.domain.document.Account;
import io.leafage.gateway.domain.document.AccountRole;
import io.leafage.gateway.domain.document.Role;
import io.leafage.gateway.repository.AccountRepository;
import io.leafage.gateway.repository.AccountRoleRepository;
import io.leafage.gateway.repository.RoleRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import static org.mockito.BDDMockito.given;

/**
 * JDBC操作用户测试类
 *
 * @author liwenqiang 2021/8/30 17:04
 */
@ExtendWith(MockitoExtension.class)
class ServerReactiveUserDetailsServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountRoleRepository accountRoleRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private ServerReactiveUserDetailsService serverReactiveUserDetailsService;

    @Test
    void findByUsername() {
        Account account = new Account();
        account.setId(new ObjectId());
        account.setUsername("little3201");
        account.setPassword("123456");
        account.setEnabled(true);
        account.setAccountExpiresAt(LocalDateTime.now().plusDays(7L));
        account.setCredentialsExpiresAt(LocalDateTime.now().plusHours(2L));
        given(this.accountRepository.getByUsername(Mockito.anyString())).willReturn(Mono.just(account));

        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(account.getId());
        accountRole.setRoleId(new ObjectId());
        given(this.accountRoleRepository.findByAccountIdAndEnabledTrue(Mockito.any(ObjectId.class))).willReturn(Flux.just(accountRole));

        Role role = new Role();
        role.setId(accountRole.getRoleId());
        role.setCode("213A9023");
        given(this.roleRepository.findById(Mockito.any(ObjectId.class))).willReturn(Mono.just(role));

        StepVerifier.create(serverReactiveUserDetailsService.findByUsername("little3201")).expectNextCount(1).verifyComplete();
    }

}