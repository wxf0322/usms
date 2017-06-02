/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.controller;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.sf.json.JSONObject;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述 登入相关控制器
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/3 16:21
 */
@Controller
@RequestMapping("/v1/openapi/")
public class LoginController {

    /**
     * 注入OAuthService
     */
    @Autowired
    private OAuthService oAuthService;

    /**
     * 登出接口
     * @param request
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @ResponseBody
    @RequestMapping(value = "loginOut")
    public ResponseEntity loginOut(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {

        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获得安全管理器
        Subject subject = SecurityUtils.getSubject();
        // 创建json
        JSONObject resultJson = new JSONObject();
        try {
            // 删除该用户所有的accessToken
            oAuthService.deleteAccountByloginName(loginName);
            // 停用该用户的session
            subject.getSession().stop();
            resultJson.put("result", true);
            return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
        } catch (Exception e) {
            resultJson.put("result", false);
            return new ResponseEntity(resultJson.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}

