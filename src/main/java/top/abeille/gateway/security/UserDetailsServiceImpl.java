/*
 * Copyright © 2010-2019 Abeille All rights reserved.
 */
package top.abeille.gateway.security;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.abeille.gateway.api.HypervisorApi;
import top.abeille.gateway.api.bo.UserBO;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 用户认证service实现
 *
 * @author liwenqiang 2018/10/18 21:18
 **/
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    /**
     * 开启日志
     */
    protected static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * email 正则
     */
    private static final String REGEX_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    /**
     * 手机号 正则
     */
    private static final String REGEX_MOBILE = "0?(13|14|15|17|18|19)[0-9]{9}";

    private final HypervisorApi hypervisorApi;

    public UserDetailsServiceImpl(HypervisorApi hypervisorApi) {
        this.hypervisorApi = hypervisorApi;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<UserBO> infoMono;
        // 判断是邮箱还是手机号
        if (isMobile(username)) {
            infoMono = hypervisorApi.fetchUserByMobile(username);
        } else if (isEmail(username)) {
            infoMono = hypervisorApi.fetchUserByEmail(username);
        } else {
            logger.error("账号错误：{}", username);
            throw new UsernameNotFoundException(username);
        }
        return this.loadUser(infoMono);
    }

    /**
     * 请求用户信息，并转载为UserDetails
     *
     * @param infoMono 用户信息
     * @return 构造的security user
     */
    private Mono<UserDetails> loadUser(Mono<UserBO> infoMono) {
        // 查配置的角色
        Mono<ArrayList<GrantedAuthority>> authorityList = infoMono.flatMap(userBO ->
                // 使用Flux::fromIterable转换成Flux<String>
                hypervisorApi.retrieveRoles(userBO.getBusinessId()).flatMapMany(Flux::fromIterable)
                        .collect(ArrayList::new, (roleIdList, businessId) -> roleIdList.add(new SimpleGrantedAuthority(businessId))));
        // 构造用户信息
        return authorityList.zipWith(infoMono, (authorities, userInfo) ->
                new User(StringUtils.isNotBlank(userInfo.getMobile()) ? userInfo.getMobile() : userInfo.getEmail(), userInfo.getPassword(),
                        userInfo.getEnabled(), userInfo.getAccountNonExpired(), userInfo.getCredentialsNonExpired(),
                        userInfo.getAccountNonLocked(), authorities));
    }

    /**
     * 是否手机号
     *
     * @param mobile 匹配字符
     * @return true if mather otherwise false
     */
    private boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 是否邮箱
     *
     * @param email 匹配字符
     * @return true if mather otherwise false
     */
    private boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
}
