package io.leafage.gateway.repository;

import io.leafage.gateway.domain.document.Account;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * account repository
 *
 * @author liwenqiang 2022-02-09 13:51
 **/
@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, ObjectId> {

    Mono<Account> getByUsername(String username);
}