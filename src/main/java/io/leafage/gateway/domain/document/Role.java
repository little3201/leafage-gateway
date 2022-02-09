package io.leafage.gateway.domain.document;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class for account.
 *
 * @author liwenqiang 2022-02-09 13:51
 **/
@Document(collection = "role")
public class Role extends AbstractDocument {

    /**
     * 代码
     */
    @Indexed(unique = true)
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 上级
     */
    @Indexed(sparse = true)
    private ObjectId superior;
    /**
     * 描述
     */
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getSuperior() {
        return superior;
    }

    public void setSuperior(ObjectId superior) {
        this.superior = superior;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
