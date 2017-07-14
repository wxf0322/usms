/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.servlet;

import net.evecom.common.usms.core.vo.ResultStatus;
import net.sf.json.JSONObject;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * 描述 通过code获得accessToken的servlet
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/7/12 上午11:12
 */
public class AccessTokenServlet extends HttpServlet {

    /**
     * @see Logger
     */
    private static Logger logger = LoggerFactory.getLogger(AccessTokenServlet.class);

    /**
     * accessTokenUrl
     */
    private String accessTokenUrl;

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;

    /**
     * 根据授权码获取accessToken
     *
     * @param authCode
     * @return accessToken响应
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    private OAuthAccessTokenResponse makeTokenRequestWithAuthCode(String authCode, String redirectUri)
            throws OAuthProblemException, OAuthSystemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(this.accessTokenUrl)
                .setClientId(this.clientId)
                .setClientSecret(this.clientSecret)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setCode(authCode)
                .setRedirectURI(redirectUri)
                .buildBodyMessage();
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthAccessTokenResponse oauthResponse = oAuthClient.accessToken(request);
        return oauthResponse;
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.accessTokenUrl = this.getInitParameter("accessTokenUrl");
        this.clientId = this.getInitParameter("clientId");
        this.clientSecret = this.getInitParameter("clientSecret");

        String code = request.getParameter("code");
        String redirectUri = URLDecoder.decode(request.getParameter("redirectUri"), "UTF-8");

        PrintWriter writer = response.getWriter();

        try {
            // 获得accessToken
            OAuthAccessTokenResponse authAccessTokenResponse =
                    this.makeTokenRequestWithAuthCode(code, redirectUri);
            String accessToken = authAccessTokenResponse.getAccessToken();

            // 将 accessToken 存入session之中
            request.getSession().setAttribute("accessToken", accessToken);

            ResultStatus resultStatus = new ResultStatus(true, "");
            JSONObject resultJson = JSONObject.fromObject(resultStatus);
            writer.write(resultJson.toString());
        } catch (OAuthProblemException | OAuthSystemException e) {
            logger.info(e.getMessage());
            ResultStatus resultStatus = new ResultStatus(false, "");
            JSONObject resultJson = JSONObject.fromObject(resultStatus);
            writer.write(resultJson.toString());
        }

    }

}
