package io.leafage.gateway.repository;

import io.leafage.gateway.domain.document.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * role repository
 *
 * @author liwenqiang 2022-02-09 13:51
 **/
@Repository
public interface RoleRepository extends ReactiveMongoRepository<Role, ObjectId> {
}
