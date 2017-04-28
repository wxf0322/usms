/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.filter;

import net.evecom.common.usms.core.model.ResultJson;
import net.evecom.common.usms.core.util.WebUtil;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.core.model.Status;
import net.sf.json.JSONObject;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述 Oauth2全局过滤器
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:49
 */
public class OAuth2Filter implements Filter {

    /**
     * 日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(OAuth2Filter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            //构建OAuth资源请求
            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(
                    (HttpServletRequest) request, ParameterStyle.QUERY);

            // 活动Access Token
            String accessToken = oauthRequest.getAccessToken();

            //验证Access Token
            if (!checkAccessToken((HttpServletRequest) request, accessToken)) {
                // 如果不存在/过期了，返回未验证错误，需重新验证
                oAuthFailResponse(httpResponse);
            }
            chain.doFilter(request, response);
        } catch (OAuthProblemException e) {
            try {
                oAuthFailResponse(httpResponse);
            } catch (OAuthSystemException ex) {
                logger.error("error trying to access oauth server", ex);
            }
        } catch (OAuthSystemException e) {
            logger.error("error trying to access oauth server", e);
        }
    }

    /**
     * oAuth认证失败时的输出
     *
     * @param response
     * @throws OAuthSystemException
     * @throws IOException
     */
    private void oAuthFailResponse(HttpServletResponse response) throws OAuthSystemException, IOException {
        OAuthResponse oauthResponse = OAuthRSResponse
                .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setRealm(Constants.RESOURCE_SERVER_NAME)
                .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                .buildHeaderMessage();

        // 添加请求头
        response.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE,
                oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
        response.addHeader(OAuth.HeaderType.CONTENT_TYPE, "text/html; charset=utf-8");

        // 编写请求
        PrintWriter writer = response.getWriter();

        // 构造响应请求
        Status status = new Status(HttpStatus.UNAUTHORIZED.getReasonPhrase(), Constants.INVALID_ACCESS_TOKEN);
        ResultJson resultJson = new ResultJson(ResultJson.FAILED, status);
        JSONObject jsonObject = JSONObject.fromObject(resultJson);
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
    }

    /**
     * 验证accessToken
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    private boolean checkAccessToken(HttpServletRequest request, String accessToken) throws IOException {
        String checkAccessCodeUrl = WebUtil.getBasePath(request) + "checkAccessToken?accessToken=";
        URL url = new URL(checkAccessCodeUrl + accessToken);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.disconnect();
        return HttpServletResponse.SC_OK == conn.getResponseCode();
    }

    @Override
    public void destroy() {
    }

}
