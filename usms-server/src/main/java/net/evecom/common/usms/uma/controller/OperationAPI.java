/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ErrorStatus;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.OperationService;
import net.evecom.common.usms.uma.service.StaffService;
import net.evecom.common.usms.uma.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 10:47
 */
@RestController
@RequestMapping("/v1/openapi")
public class OperationAPI {
    /**
     * operationService的注入
     */
    @Resource
    private OperationService operationService;

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


    @RequestMapping(value = "/operation/exist",produces = "application/json; charset=UTF-8")
    public ResponseEntity getOperation(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        String operationName = request.getParameter("operation");
        if(StringUtils.isEmpty(operationName)){
            ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_PARAMS)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        }

        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();

        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获得用户实体类
        UserEntity user = userService.findByLoginName(loginName);

        JSONObject jsonObject = new JSONObject();
        if (operationService.hasOperation(user.getId(), operationName)) {
            jsonObject.put("result", true);
        } else jsonObject.put("result", false);
        return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/operations", produces = "application/json; charset=UTF-8")
    public ResponseEntity getOperations(HttpServletRequest request) {
        String application = request.getParameter("application");
        if(StringUtils.isEmpty(application)){
            ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_PARAMS)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        }
        List<OperationEntity> operations =
                operationService.getOperationsByAppName(application);
        // 构造操作
        JSONArray operJsonArr = new JSONArray();
        for (OperationEntity operation : operations) {
            JSONObject operJson = new JSONObject();
            operJson.put("id", operation.getId());
            operJson.put("label", operation.getLabel());
            operJson.put("name", operation.getName());
            operJson.put("manual_sn", operation.getManualSn());
            operJson.put("parent_id", operation.getParentId());
            operJson.put("tree_level", operation.getTreeLevel());
            operJson.put("path", operation.getPath());
            operJson.put("res_url", operation.getResUrl());
            operJson.put("icon_path", operation.getIconPath());
            operJson.put("opt_type", operation.getOptType());
            operJsonArr.add(operJson);
        }
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("operations",operJsonArr);
        return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }

}
