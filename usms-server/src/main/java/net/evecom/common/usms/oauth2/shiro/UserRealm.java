/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.shiro;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 描述 用户作用域
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/2 15:42
 */
@Service
public class UserRealm extends AuthorizingRealm {

    /**
     * 注入userService
     */
    @Autowired
    private UserService userService;

    /**
     * 认证方法
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //从 token 中，获取用户身份信息
        String loginName = (String) authenticationToken.getPrincipal();
        UserEntity user = userService.getUserByLoginName(loginName);

        if (user == null) {
            // 用户名不存在，抛出异常
            throw new UnknownAccountException();
        }
        if (user.getEnabled() == 0) {
            // 帐号被锁定，抛出异常
            throw new LockedAccountException();
        }

        //返回认证信息由父类AuthenticatingRealm进行认证
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getLoginName(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                getName());


        return authenticationInfo;
    }

    /**
     * 授权方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
