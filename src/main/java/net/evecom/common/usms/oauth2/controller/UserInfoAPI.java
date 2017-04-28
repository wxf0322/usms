/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.controller;

import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.oauth2.service.StaffService;
import net.evecom.common.usms.oauth2.service.UserService;
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
@RequestMapping("/v1/openapi")
public class UserInfoAPI {

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
     * 注入StaffService
     */
    @Autowired
    private StaffService staffService;

    private JSONObject getStaffJSONObject(Long userId) {
        // 获得员工实体类
        StaffEntity staff = staffService.findOne(userId);
        if (staff == null) {
            return null;
        }
        // 构造机构信息
        List<InstitutionEntity> institutions = staff.getInstitutions();
        JSONArray instJsonArr = new JSONArray();
        for (InstitutionEntity institution : institutions) {
            JSONObject instJson = new JSONObject();
            instJson.put("id", institution.getId());
            instJson.put("label", institution.getLabel());
            instJson.put("name", institution.getName());
            instJson.put("tree_label", institution.getLabel());
            instJson.put("parent_id", institution.getParentId());
            instJson.put("manual_sn", institution.getManualSn());
            instJsonArr.add(instJson);
        }
        // 构造员工
        JSONObject staffJson = new JSONObject();
        staffJson.put("moblie", staff.getMobile());
        staffJson.put("sex", staff.getSex());
        staffJson.put("offical_post", staff.getOfficalPost());
        staffJson.put("institutions", instJsonArr);
        return staffJson;
    }

    private JSONArray getOperJSONArray(Long userId) {
        // 获得员工所有的操作
        List<OperationEntity> operations = userService.findOperationsById(userId);
        // 构造操作
        JSONArray operJsonArr = new JSONArray();
        for (OperationEntity operation : operations) {
            JSONObject operJson = new JSONObject();
            operJson.put("id", operation.getId());
            operJson.put("label", operation.getLabel());
            operJson.put("name", operation.getName());
            operJson.put("tree_level", operation.getTreeLevel());
            operJson.put("parent_id", operation.getParentId());
            operJson.put("manual_sn", operation.getManualSn());
            operJsonArr.add(operJson);
        }
        return operJsonArr;
    }

    private JSONArray getAppJSONArray(Long userId) {
        // 获得用户所有的应用
        List<ApplicationEntity> applications = userService.findApplicationsById(userId);
        // 构造应用
        JSONArray appJsonArr = new JSONArray();
        for (ApplicationEntity application : applications) {
            JSONObject appJson = new JSONObject();
            appJson.put("label", application.getLabel());
            appJson.put("name", application.getName());
            appJsonArr.add(appJson);
        }
        return appJsonArr;
    }

    @RequestMapping(value = "/userInfo", produces = "application/json; charset=UTF-8")
    public ResponseEntity userInfo(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();

        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获得用户实体类
        UserEntity user = userService.findByLoginName(loginName);

        Long userId = user.getId();
        JSONObject staffJson = getStaffJSONObject(userId);
        JSONArray operJsonArr = getOperJSONArray(userId);
        JSONArray appJsonArr = getAppJSONArray(userId);

        JSONObject userJson = new JSONObject();
        userJson.put("id", user.getId());
        userJson.put("name", user.getName());
        userJson.put("loginName", user.getLoginName());
        userJson.put("staff", staffJson);
        userJson.put("operations", operJsonArr);
        userJson.put("applications", appJsonArr);

        JSONObject jsonObject = JSONObject.fromObject(userJson);
        return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }

}