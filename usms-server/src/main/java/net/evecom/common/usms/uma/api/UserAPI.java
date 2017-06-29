/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.uma.service.StaffService;
import net.evecom.common.usms.vo.InstitutionVO;
import net.evecom.common.usms.vo.OperationVO;
import net.evecom.common.usms.vo.StaffVO;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.ApplicationService;
import net.evecom.common.usms.uma.service.OperationService;
import net.evecom.common.usms.uma.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述 用户信息接口
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:50
 */
@RestController
@RequestMapping("/v1/openapi/user")
public class UserAPI {

    /**
     * 注入OAuthService
     */
    @Autowired
    private OAuthService oAuthService;

    /**
     * 注入UserService
     */
    @Autowired
    private UserService userService;

    /**
     * 注入ApplicationService
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * 注入OperationService
     */
    @Autowired
    private OperationService operationService;

    /**
     * @see StaffService
     */
    @Autowired
    private StaffService staffService;

    /**
     * 获得员工JSON对象
     *
     * @param staffId
     * @return
     */
    private JSONObject getStaffJSONObject(Long staffId) {
        // 获得员工实体类
        StaffEntity staffEntity = staffService.findOne(staffId);
        if (staffEntity == null) return new JSONObject();
        // 获得用户model
        StaffVO staffVO = new StaffVO(staffEntity);
        JSONObject staffJson = JSONObject.fromObject(staffVO);
        return staffJson;
    }

    /**
     * 获得组织机构数组
     *
     * @param userId
     * @return
     */
    private JSONArray getInstitutionJSONArray(Long userId) {
        UserEntity userEntity = userService.findOne(userId);
        // 构造机构信息
        List<InstitutionEntity> institutions = userEntity.getInstitutions();
        JSONArray instJsonArr = new JSONArray();
        for (InstitutionEntity institution : institutions) {
            InstitutionVO institutionVO = new InstitutionVO(institution);
            JSONObject instJson = JSONObject.fromObject(institutionVO);
            instJson.remove("enabled");
            instJsonArr.add(instJson);
        }
        return instJsonArr;
    }

    /**
     * 获得应用JSON数组
     *
     * @param clientId
     * @return
     */
    private JSONObject getApplicationJSONObject(String clientId) {
        ApplicationEntity application = applicationService.getAppByClientId(clientId);

        JSONObject resultJson = new JSONObject();
        resultJson.put("id", application.getId());
        resultJson.put("label", application.getLabel());
        resultJson.put("name", application.getName());
        resultJson.put("clientId", application.getClientId());
        resultJson.put("clientSecret", application.getClientSecret());

        String appName = application.getName();
        List<OperationEntity> operations = operationService.listOpersByAppName(appName);

        // 构造操作
        JSONArray operJsonArr = new JSONArray();
        for (OperationEntity operation : operations) {
            OperationVO operationVO = new OperationVO(operation);
            JSONObject operJson = JSONObject.fromObject(operationVO);
            operJson.remove("enabled");
            operJson.remove("applicationId");
            operJsonArr.add(operJson);
        }

        resultJson.put("operations", operJsonArr);
        return resultJson;
    }

    /**
     * 获得用户信息
     *
     * @param request
     * @return
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     */
    @RequestMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity getUser(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获取ClientId
        String clientId = oAuthService.getClientIdByAccessToken(accessToken);

        // 获得用户实体类
        UserEntity user = userService.getUserByLoginName(loginName);
        // 获得员工id
        Long staffId = (user.getStaffEntity() != null) ? user.getStaffEntity().getId() : null;

        // 获得相应的json对象
        JSONObject staffJson = getStaffJSONObject(staffId);
        JSONObject appJson = getApplicationJSONObject(clientId);
        JSONArray instJsonArr = getInstitutionJSONArray(user.getId());

        // 构造userJson
        JSONObject resultJson = new JSONObject();
        resultJson.put("id", user.getId());
        resultJson.put("name", user.getName());
        resultJson.put("loginName", user.getLoginName());
        resultJson.put("staff", staffJson);
        resultJson.put("institutions", instJsonArr);
        resultJson.put("application", appJson);

        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

}