/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.controller;

import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.service.ApplicationService;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.PasswordHelper;
import net.evecom.common.usms.uma.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 描述 权限Controller层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:38
 */
@Controller
public class AuthorizeController {

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
     * 注入UserService
     */
    @Autowired
    private UserService userService;

    /**
     * 注入密码加密工具类
     */
    @Autowired
    private PasswordHelper passwordHelper;


    /**
     * 获得授权码
     * client_id错误或者失效，返回400
     * 重定向成功，返回302
     * redirect_url未空，返回404
     * 其他错误，返回400
     *
     * @param model
     * @param request
     * @return
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    @RequestMapping(value = "/authorize", produces = "application/json; charset=UTF-8")
    public Object authorize(Model model, HttpServletRequest request) throws URISyntaxException, OAuthSystemException {
        try {
            // 构建 OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            // 获得授权码
            String clientId = oauthRequest.getClientId();

            // 检查传入的客户端id是否正确
            if (!oAuthService.checkClientId(clientId)) {
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(Constants.INVALID_CLIENT_ID)
                        .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 如果用户没有登录，跳转到登陆页面
            if (!login(request)) { // 登录失败时跳转到登陆页面
                model.addAttribute("client", applicationService.findByClientId(clientId));
                return "oauth2login";
            }

            // 获取用户名
            String loginName = request.getParameter("loginName");

            // 生成授权码
            String authorizationCode = null;

            // responseType目前仅支持CODE，另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                authorizationCode = oAuthService.getNewAuthCode(loginName, clientId);
            }

            // 进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
                    OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);

            // 设置授权码
            builder.setCode(authorizationCode);

            // 得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            // 构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

            // 根据OAuthResponse返回ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            //出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                //告诉客户端没有传入redirectUri直接报错
                OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_REQUEST)
                        .setErrorDescription(Constants.INVALID_REDIRECT_URI)
                        .buildJSONMessage();
                return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 返回错误消息（如?error=）
            final OAuthResponse response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .location(redirectUri)
                    .buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }
    }

    /**
     * 登入判断
     *
     * @param request
     * @return
     */
    private boolean login(HttpServletRequest request) {
        // loginName与password只能用post请求提交
        if ("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");

        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            request.setAttribute("error", Constants.EMPTY_ACCOUNT_OR_PASSWORD);
            return false;
        }

        UserEntity user = userService.findByLoginName(loginName);
        if (user == null) {
            request.setAttribute("error", Constants.UNKNOWN_ACCOUNT);
            return false;
        }

        // 加密过后的密码
        String encryptPwd = passwordHelper.encryptPassword(loginName, password, user.getSalt());

        // 传入加密过的密码
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, encryptPwd);
        Subject subject = SecurityUtils.getSubject();
        String error = null;
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            error = Constants.INCORRECT_CREDENTIALS;
        } catch (UnknownAccountException e) {
            error = Constants.UNKNOWN_ACCOUNT;
        } catch (LockedAccountException e) {
            error = Constants.LOCKED_ACCOUNT;
        } catch (AuthenticationException e) {
            error = e.getMessage();
        }
        if (error != null) {
            request.setAttribute("error", error);
            return false;
        } else {
            return true;
        }
    }

}