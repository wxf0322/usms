/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.PrivilegeService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 10:48
 */
@RestController
@RequestMapping("/v1/openapi/")
public class PrivilegeAPI {
    @Resource
    private PrivilegeService privilegeService;
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



   @RequestMapping(value = "privilege/exist",produces = "application/json; charset=UTF-8")
    public ResponseEntity getPrivilege(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
      String privilege = request.getParameter("privilege");
       // 构建OAuth资源请求
       OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
       // 获取Access Token
       String accessToken = oauthRequest.getAccessToken();
       // 获取用户名
       String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
       // 获得用户实体类
       UserEntity user = userService.findByLoginName(loginName);
       List<PrivilegeEntity>  privilegeEntities =
               privilegeService.getPrivilegesByUserId(user.getId());
       JSONObject jsonObject = new JSONObject();
       for(PrivilegeEntity privilegeEntity :privilegeEntities){
           if(privilegeEntity.getLabel().toString().equals(privilege)){
               jsonObject.put("result",true);
               return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
           }
       }
       jsonObject.put("result",false);
       return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }



    @RequestMapping(value = "privileges",produces = "application/json; charset=UTF-8")
    public ResponseEntity getPrivileges(HttpServletRequest request) {
        String application = request.getParameter("application");
        List<PrivilegeEntity> privileges =
                privilegeService.getPrivilegesByAppName(application);
        // 构造操作
        JSONArray priJsonArr = new JSONArray();
        for (PrivilegeEntity privilege : privileges) {
            JSONObject priJson = new JSONObject();
            priJson.put("id", privilege.getId());
            priJson.put("label", privilege.getLabel());
            priJson.put("name", privilege.getName());
            priJson.put("enabled", privilege.getEnabled());
            priJsonArr.add(priJson);
        }
        JSONObject jsonObject =new JSONObject();
        jsonObject.put("privileges",priJsonArr);
        return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }
}
