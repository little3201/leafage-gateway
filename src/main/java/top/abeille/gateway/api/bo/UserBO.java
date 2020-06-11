/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */
package top.abeille.gateway.api.bo;

import java.io.Serializable;

/**
 * BO class for User
 *
 * @author liwenqiang
 */
public class UserBO implements Serializable {

    private static final long serialVersionUID = 3745288479594725090L;
    /**
     * 业务ID
     */
    private String businessId;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 账户是否有效
     */
    private Boolean accountNonExpired;
    /**
     * 是否锁定
     */
    private Boolean accountNonLocked;
    /**
     * 认证是否有效
     */
    private Boolean credentialsNonExpired;
    /**
     * 是否有效
     */
    private Boolean enabled;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
