/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/2 15:42
 */
public class RealmService extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
         //从token中 获取用户身份信息
        String username = (String) authenticationToken.getPrincipal();

        //拿username从数据库中查询
        //....

        //如果查询不到则返回null
        if (!username.equals("zhang")) {//这里模拟查询不到
            return null;
        }

        //获取从数据库查询出来的用户密码
        String password = "123";
        //这里使用静态数据模拟。。

        //返回认证信息由父类AuthenticatingRealm进行认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                username, password, getName());

        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
