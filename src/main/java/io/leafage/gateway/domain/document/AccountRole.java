package io.leafage.gateway.domain.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Model class for account role.
 *
 * @author liwenqiang 2022-02-09 13:51
 **/
@Document(collection = "account_role")
public class AccountRole extends AbstractDocument {

    /**
     * 账号主键
     */
    @Indexed
    @Field(value = "account_id")
    private ObjectId accountId;
    /**
     * 角色主键
     */
    @Indexed
    @Field(value = "role_id")
    private ObjectId roleId;

    public ObjectId getAccountId() {
        return accountId;
    }

    public void setAccountId(ObjectId accountId) {
        this.accountId = accountId;
    }

    public ObjectId getRoleId() {
        return roleId;
    }

    public void setRoleId(ObjectId roleId) {
        this.roleId = roleId;
    }
}
