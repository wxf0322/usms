/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.core.vo.ErrorStatus;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.oauth2.service.OAuthService;
import net.evecom.common.usms.uma.service.RoleService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Pisces Lu
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
     * 注入
     */
    @Autowired
    private RoleService roleService;

    /**
     * 判断是否拥有该角色
     *
     * @param request
     * @return ResponseEntity
     */
    @RequestMapping(value = "/role/exist", produces = "application/json; charset=UTF-8")
    public ResponseEntity hasPrivilege(HttpServletRequest request) throws OAuthProblemException, OAuthSystemException {
        String roleName = request.getParameter("role");
        if (StringUtils.isEmpty(roleName)) {
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
        UserEntity user = userService.getUserByLoginName(loginName);
        JSONObject jsonObject = new JSONObject();
        if (roleService.hasRole(user.getId(), roleName)) {
            jsonObject.put("result", true);
        } else {
            jsonObject.put("result", false);
        }
        return new ResponseEntity(jsonObject.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/roles", produces = "application/json; charset=UTF-8")
    public ResponseEntity getRoles(HttpServletRequest request) {
        List<RoleEntity> roles = roleService.findAll();
        JSONArray roleJsonArr = new JSONArray();
        for(RoleEntity role : roles) {
            if (role.getEnabled() == 0) continue;
            JSONObject roleJson = JSONObject.fromObject(role);
            roleJson.remove("enabled");
            roleJsonArr.add(roleJson);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("roles", roleJsonArr);
        return new ResponseEntity(resultJson.toString(), HttpStatus.OK);
    }

}

