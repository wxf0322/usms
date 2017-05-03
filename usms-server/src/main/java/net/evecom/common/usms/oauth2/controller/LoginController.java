/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.controller;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.oauth2.service.OAuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/3 16:21
 */
@Controller
public class LoginController {

    @Autowired
    private OAuthService oAuthService;

    @ResponseBody
    @RequestMapping(value = "loginOut")
    public String loginOut() {
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = (UserEntity) subject.getSession().getAttribute("user");
        String loginName = user.getLoginName();
        // 删除该用户所有的accessToken
        oAuthService.deleteAccountByloginName(loginName);
        // 停用该用户的session
        subject.getSession().stop();
        return "login out success!";
    }
}


