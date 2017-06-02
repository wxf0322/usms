/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.model.InstitutionModel;
import net.evecom.common.usms.model.OperationModel;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.StaffService;
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
     * 注入StaffService
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
        StaffEntity staff = staffService.findOne(staffId);
        if (staff == null) return new JSONObject();
        JSONObject staffJson = JSONObject.fromObject(staff);
        staffJson.remove("id");
        staffJson.remove("lastModified");
        staffJson.remove("modifierId");
        staffJson.remove("modifier");
        staffJson.remove("timeCreated");
        staffJson.remove("creatorId");
        staffJson.remove("creator");
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
            InstitutionModel institutionModel = new InstitutionModel(institution);
            JSONObject instJson = JSONObject.fromObject(institutionModel);
            instJson.remove("enabled");
            instJsonArr.add(instJson);
        }
        return instJsonArr;
    }

    /**
     * 获得操作JSON数组
     *
     * @param userId
     * @return
     */
    private JSONArray getOperationJSONArray(Long userId) {
        // 获得员工所有的操作
        List<OperationEntity> operations = userService.findOperationsById(userId);
        // 构造操作
        JSONArray operJsonArr = new JSONArray();
        for (OperationEntity operation : operations) {
            OperationModel operationModel = new OperationModel(operation);
            JSONObject operJson = JSONObject.fromObject(operationModel);
            operJson.remove("enabled");
            operJsonArr.add(operJson);
        }
        return operJsonArr;
    }

    /**
     * 获得应用JSON数组
     *
     * @param userId
     * @return
     */
    private JSONArray getApplicationJSONArray(Long userId) {
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
        // 获得用户实体类
        UserEntity user = userService.findByLoginName(loginName);
        // 获得员工id
        Long staffId = (user.getStaffEntity() != null) ? user.getStaffEntity().getId() : null;
        // 获得相应的json对象
        JSONObject staffJson = getStaffJSONObject(staffId);
        JSONArray operJsonArr = getOperationJSONArray(user.getId());
        JSONArray appJsonArr = getApplicationJSONArray(user.getId());
        JSONArray instJsonArr = getInstitutionJSONArray(user.getId());

        // 构造userJson
        JSONObject userJson = new JSONObject();
        userJson.put("id", user.getId());
        userJson.put("name", user.getName());
        userJson.put("loginName", user.getLoginName());
        userJson.put("staff", staffJson);
        userJson.put("institutions", instJsonArr);
        userJson.put("operations", operJsonArr);
        userJson.put("applications", appJsonArr);

        JSONObject resultJson = JSONObject.fromObject(userJson);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

}