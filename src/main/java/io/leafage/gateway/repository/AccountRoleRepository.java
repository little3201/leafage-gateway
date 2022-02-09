package io.leafage.gateway.repository;

import io.leafage.gateway.domain.document.AccountRole;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * account role repository
 *
 * @author liwenqiang 2022-02-09 13:51
 **/
@Repository
public interface AccountRoleRepository extends ReactiveMongoRepository<AccountRole, ObjectId> {

    /**
     * 根据账号查询关联角色
     *
     * @param accountId 账号主键
     * @return 关联的角色
     */
    Flux<AccountRole> findByAccountIdAndEnabledTrue(ObjectId accountId);
}
