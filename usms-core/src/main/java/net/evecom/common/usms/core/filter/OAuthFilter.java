/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述 oAuth2对接核心过滤器
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/7/6 下午4:38
 */
public class OAuthFilter implements Filter {

    /**
     * clientId
     */
    private String clientId;

    /**
     * clientSecret
     */
    private String clientSecret;

    /**
     * 服务器地址
     */
    private String serverUrl;

    /**
     * accessToken地址
     */
    private String tokenUrl;

    /**
     * 授权码地址
     */
    private String authUrl;

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
                .tokenLocation(this.tokenUrl)
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
     * 验证accessToken是否有效
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    private boolean checkAccessToken(HttpServletRequest request, String accessToken)
            throws IOException {
        String checkAccessCodeUrl = this.serverUrl + "/checkAccessToken?access_token=";
        URL url = new URL(checkAccessCodeUrl + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.disconnect();
        return HttpServletResponse.SC_OK == conn.getResponseCode();
    }

    /**
     * 删除url中的参数
     *
     * @param queryString
     * @param params
     * @return
     */
    private String removeParams(String queryString, String... params) {
        for (String param : params) {
            String keyValue = param + "=[^&]*?";
            queryString = queryString.replaceAll("(&" + keyValue + "(?=(&|$))|^" + keyValue + "(&|$))", "");
        }
        return queryString;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.clientId = filterConfig.getInitParameter("clientId");
        this.clientSecret = filterConfig.getInitParameter("clientSecret");
        this.serverUrl = filterConfig.getInitParameter("serverUrl");
        this.tokenUrl = this.serverUrl + "/authorize";
        this.authUrl = this.serverUrl + "/accessToken";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获得 httpRequest, httpResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 获得 session 对象
        HttpSession session = httpRequest.getSession();

        // 获得授权码
        String authCode = request.getParameter("code");

        // 获得session中的accessToken
        String accessToken = (String) session.getAttribute("accessToken");

        // 获得请求地址
        String requestUrl = httpRequest.getRequestURL().toString();

        // 授权码不为空
        if (StringUtils.isNotEmpty(authCode)) {
            try {
                // 携带授权码向认证服务器发送请求，并获得 accessToken
                OAuthAccessTokenResponse oauthResponse = makeTokenRequestWithAuthCode(authCode, requestUrl);
                // 将accessToken存入session
                session.setAttribute("accessToken", oauthResponse.getAccessToken());
                chain.doFilter(request, response);
            } catch (OAuthProblemException | OAuthSystemException e) {
                // 如果出现授权码错误，删除code参数
                String urlQueryString = this.removeParams(httpRequest.getQueryString(), "code");
                String redirectUrl;
                StringBuilder sb = new StringBuilder();
                if (StringUtils.isNotEmpty(urlQueryString)) {
                    sb.append(httpRequest.getRequestURL()).append("?").append(urlQueryString);
                    redirectUrl = sb.toString();
                } else {
                    redirectUrl = httpRequest.getRequestURL().toString();
                }
                httpResponse.sendRedirect(redirectUrl);
            }
        } else {
            // 构造重定向地址
            StringBuilder sb = new StringBuilder();
            sb.append(this.authUrl).append("?client_id=")
                    .append(this.clientId)
                    .append("&response_type=code&redirect_uri=")
                    .append(requestUrl);
            String redirectUrl = sb.toString();

            // 如果session中有令牌，且access_token有效，则继续请求，否则重定向至OAuth2服务器进行认证
            if (StringUtils.isNotEmpty(accessToken) && checkAccessToken(httpRequest, accessToken)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(redirectUrl);
            }
        }
    }

    @Override
    public void destroy() {
    }

}

