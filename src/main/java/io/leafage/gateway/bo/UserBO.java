package io.leafage.gateway.bo;

import java.io.Serializable;

/**
 * BO class for User .
 *
 * @author liwenqiang 2019-03-03 22:55
 */
public class UserBO implements Serializable {

    private static final long serialVersionUID = 3395523338048416407L;

    /**
     * 账号
     */
    private String username;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;

    public UserBO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
