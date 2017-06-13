/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.api;

import net.evecom.common.usms.core.model.ErrorStatus;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.UserModel;
import net.evecom.common.usms.oauth2.Constants;
import net.evecom.common.usms.uma.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述 返回用户列表接口
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 9:58
 */
@Controller
public class UsersAPI {

    /**
     * 注入GridService
     */
    @Autowired
    private GridService gridService;

    /**
     * 注入InstitutionService
     */
    @Autowired
    private InstitutionService institutionService;

    /**
     * 注入ApplicationService
     */
    @Autowired
    private ApplicationService applicationService;

    /**
     * 注入PrivilegeService
     */
    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 注入OperationService
     */
    @Autowired
    private OperationService operationService;

    /**
     * 注入RoleService
     */
    @Autowired
    private RoleService roleService;

    /**
     * 注入StaffService
     */
    @Autowired
    private StaffService staffService;

    /**
     * 注入UserService
     */
    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/openapi/users", produces = "application/json; charset=UTF-8")
    public ResponseEntity getUsers(HttpServletRequest request) {
        List<UserEntity> users = null;
        //获取管辖区域编码
        String gridCode = request.getParameter("grid");
        if (StringUtils.isNotEmpty(gridCode)) {
            users = gridService.findUsersByGridCode(gridCode);
        }
        //获取组织机构编码
        String instName = request.getParameter("institution");
        if (StringUtils.isNotEmpty(instName)) {
            users = institutionService.findUsersByInstName(instName);
        }
        //获取应用编码
        String appName = request.getParameter("application");
        if (StringUtils.isNotEmpty(appName)) {
            users = applicationService.findUsersByAppName(appName);
        }
        //获取权限编码
        String privName = request.getParameter("privilege");
        if (StringUtils.isNotEmpty(privName)) {
            users = privilegeService.findUsersByPrivName(privName);
        }
        //获取操作编码
        String operName = request.getParameter("operation");
        if (StringUtils.isNotEmpty(operName)) {
            users = operationService.findUsersByOperName(operName);
        }
        //获取角色编码
        String roleName = request.getParameter("role");
        if (StringUtils.isNotEmpty(roleName)) {
            users = roleService.findUsersByRoleName(roleName);
        }
        String officalPost = request.getParameter("offical_post");
        if (StringUtils.isNotEmpty(officalPost)) {
            users = staffService.findUsersByOfficalPost(officalPost);
        }
        JSONObject usersJson = new JSONObject();
        if (users == null) {
            ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_PARAMS)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        } else {
            usersJson.put("users", getUsersJSONArray(users));
            return new ResponseEntity(usersJson.toString(), HttpStatus.OK);
        }
    }

    /**
     * 内部接口获得用户列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/internalapi/users", produces = "application/json; charset=UTF-8")
    public ResponseEntity getUsersToMessageCenter(HttpServletRequest request) {
        List<UserEntity> users = null;
        String loginNames = request.getParameter("login_names");
        if (StringUtils.isNotEmpty(loginNames)) {
            users = userService.findByLoginNames(loginNames.split(","));
        }
        String roleNames = request.getParameter("roles");
        if (StringUtils.isNotEmpty(roleNames)) {
            users = roleService.findUsersByRoleNames(roleNames.split(","));
        }
        String instNames = request.getParameter("institutions");
        if (StringUtils.isNotEmpty(instNames)) {
            users = institutionService.findUsersByInstNames(instNames.split(","));
        }
        JSONObject usersJson = new JSONObject();
        if (users == null) {
            ErrorStatus errorStatus = new ErrorStatus
                    .Builder(ErrorStatus.INVALID_PARAMS, Constants.INVALID_PARAMS)
                    .buildJSONMessage();
            return new ResponseEntity(errorStatus.getBody(), HttpStatus.BAD_REQUEST);
        } else {
            usersJson.put("users", getUsersJSONArray(users));
            return new ResponseEntity(usersJson.toString(), HttpStatus.OK);
        }
    }

    /**
     * 转换用户数据为JSONArray
     *
     * @param users
     * @return
     */
    private JSONArray getUsersJSONArray(List<UserEntity> users) {
        if (users == null) {
            return new JSONArray();
        } else {
            JSONArray usersJsonArr = new JSONArray();
            for (UserEntity user : users) {
                UserModel userModel = new UserModel(user);
                JSONObject userJson = JSONObject.fromObject(userModel);
                userJson.remove("enabled");
                userJson.remove("roleNames");
                usersJsonArr.add(userJson);
            }
            return usersJsonArr;
        }
    }

}

