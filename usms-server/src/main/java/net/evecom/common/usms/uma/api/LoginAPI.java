/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.core.model.ErrorStatus;
import net.evecom.common.usms.core.util.WebUtil;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.ApplicationService;
import org.apache.commons.lang.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述 登入相关控制器
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/3 16:21
 */
@Controller
@RequestMapping("/v1/openapi/")
public class LoginAPI {

    /**
     * 注入OAuthService
     */
    @Autowired
    private OAuthService oAuthService;

    /**
     * 注入ApplicationService
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * 登出接口
     *
     * @param request
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @RequestMapping(value = "loginOut")
    public Object loginOut(HttpServletRequest request, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 重定向地址
        String redirectUri = request.getParameter("redirect_uri");

        // 获得Client id
        String clientId = request.getParameter("client_id");

        if (StringUtils.isEmpty(redirectUri) || StringUtils.isEmpty(clientId)) {
            ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_PARAMS)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        }

        if (applicationService.findByClientId(clientId) == null) {
             ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_CLIENT_ID)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        }

        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获得安全管理器
        Subject subject = SecurityUtils.getSubject();
        // 删除该用户所有的accessToken
        oAuthService.deleteAccountByLoginName(loginName);
        // 停用该用户的session
        subject.getSession().stop();

        // 获取basePath
        String basePath = WebUtil.getBasePath(request);
        // 构造请求地址
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append(basePath)
                .append("authorize?client_id=")
                .append(clientId)
                .append("&response_type=code&redirect_uri=")
                .append(redirectUri);

        return "redirect:" + loginUrl.toString();
    }
}

