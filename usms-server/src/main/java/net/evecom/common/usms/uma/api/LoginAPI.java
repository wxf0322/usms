/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.core.vo.ErrorStatus;
import net.evecom.common.usms.core.util.WebUtil;
import net.evecom.common.usms.core.vo.ResultStatus;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.oauth2.shiro.ShiroSecurityHelper;
import net.evecom.common.usms.uma.service.ApplicationService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginAPI {

    /**
     * @see Logger
     */
    private static Logger logger = LoggerFactory.getLogger(LoginAPI.class);

    /**
     * @see OAuthService
     */
    @Autowired
    private OAuthService oAuthService;

    /**
     * @see ShiroSecurityHelper
     */
    @Autowired
    private ShiroSecurityHelper shiroSecurityHelper;

    /**
     * 登出接口
     *
     * @param request
     * @return
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @ResponseBody
    @RequestMapping(value = "logout")
    public ResponseEntity logout(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);

        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();

        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);

        // 获得session与sessionDAO
        Session session = shiroSecurityHelper.getSessionByLoginName(loginName);
        SessionDAO sessionDAO = shiroSecurityHelper.getSessionDAO();

        try {
            sessionDAO.delete(session);
            oAuthService.deleteAccountByLoginName(loginName);
            logger.info("[{}] 登出系统成功！", loginName);
            ResultStatus resultStatus = new ResultStatus(true, "");
            JSONObject resultJson = JSONObject.fromObject(resultStatus);
            return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.info("[{}] 登出系统失败！", loginName);
            ResultStatus resultStatus = new ResultStatus(false, "");
            JSONObject resultJson = JSONObject.fromObject(resultStatus);
            return new ResponseEntity(resultJson.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}

