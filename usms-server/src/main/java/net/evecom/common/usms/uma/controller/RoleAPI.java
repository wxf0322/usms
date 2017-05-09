/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.dao.RoleDao;
import net.evecom.common.usms.uma.service.RoleService;
import net.evecom.common.usms.uma.service.UserService;
import net.sf.json.JSONObject;
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
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 18:13
 */
@RestController
@RequestMapping("/v1/openapi")
public class RoleAPI {
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
     *注入
     */
    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/role/exist",produces = "application/json; charset=UTF-8")
    public ResponseEntity getPrivileges(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
      String role = request.getParameter("role");
        // 构建OAuth资源请求
        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
        // 获取Access Token
        String accessToken = oauthRequest.getAccessToken();
        // 获取用户名
        String loginName = oAuthService.getLoginNameByAccessToken(accessToken);
        // 获得用户实体类
        UserEntity user = userService.findByLoginName(loginName);
        List<RoleEntity> roleEntities =
                roleService.findRolesByUserId(user.getId());
        JSONObject jsonObject = new JSONObject();
        for(RoleEntity roleEntity :roleEntities){
            if(roleEntity.getName().toString().equals(role)){
                jsonObject.put("result",true);
                return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
            }
        }
        jsonObject.put("result",false);
        return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }
}

