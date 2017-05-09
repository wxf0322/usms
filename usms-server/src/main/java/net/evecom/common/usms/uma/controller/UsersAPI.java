/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 9:58
 */
@Controller
@RequestMapping("/v1/openapi/users")
public class UsersAPI {
    /**
     * 注入GridService
     */
    @Autowired
    private GridService gridService;

    /**
     * 注入InstitutinoService
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
     * 获取用户列表
     *
     * @param request
     * @return
     */
    @RequestMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity getUsers(HttpServletRequest request) {
        JSONObject usersJson = new JSONObject();
        List<UserEntity> users = null;
        //获取管辖区域编码
        String gridName = request.getParameter("grid");
        if (gridName != null) {
            users = getUsersByGridName(gridName);
        }
        //获取组织机构编码
        String instName = request.getParameter("institution");
        if (instName != null) {
            users = getUsersByInstName(instName);
        }
        //获取应用编码
        String appName = request.getParameter("application");
        if (appName != null) {
            users = getUsersByApplicationName(appName);
        }
        //获取权限编码
        String privName = request.getParameter("privilege");
        if (privName != null) {
            users = getUsersByPrivName(privName);
        }
        //获取操作编码
        String operName = request.getParameter("operation");
        if (operName != null) {
            users = getUsersByOperName(operName);
        }
        //获取角色编码
        String roleName = request.getParameter("role");
        if (roleName != null) {
            users = getUsersByRoleName(roleName);
        }
        usersJson.put("users", getUsersJSONArray(users));
        return new ResponseEntity(usersJson.toString(), HttpStatus.OK);
    }

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridName
     * @return
     */
    List<UserEntity> getUsersByGridName(String gridName) {
        return gridService.getUsersByGridName(gridName);
    }

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    List<UserEntity> getUsersByInstName(String instName) {
        return institutionService.getUsersByInstName(instName);
    }

    /**
     * 根据应用编码构查询用户列表
     *
     * @param appName
     * @return
     */
    List<UserEntity> getUsersByApplicationName(String appName) {
        return applicationService.getUsersByApplicationName(appName);
    }

    /**
     * 根据组权限编码构查询用户列表
     *
     * @param privName
     * @return
     */
    List<UserEntity> getUsersByPrivName(String privName) {
        return privilegeService.getUsersByPrivName(privName);
    }

    /**
     * 根据操作编码构查询用户列表
     *
     * @param operName
     * @return
     */
    List<UserEntity> getUsersByOperName(String operName) {
        return operationService.getUsersByOperName(operName);
    }

    /**
     * 根据角色编码构查询用户列表
     *
     * @param roleName
     * @return
     */
    List<UserEntity> getUsersByRoleName(String roleName) {
        return roleService.getUsersByRoleName(roleName);
    }

    /**
     * 转换用户数据为JSONArray
     *
     * @param users
     * @return
     */
    private JSONArray getUsersJSONArray(List<UserEntity> users) {
        if (users.size() == 0) {
            return new JSONArray();
        } else {
            JSONArray usersArray = new JSONArray();
            for (UserEntity user : users) {
                JSONObject userJson = new JSONObject();
                userJson.put("id", user.getId());
                userJson.put("login_name", user.getLoginName());
                userJson.put("name", user.getName());
                userJson.put("enabled", user.getEnabled());
                userJson.put("staff_id", user.getStaffId());
                userJson.put("remarks", user.getRamarks());
                usersArray.add(userJson);
            }
            return usersArray;
        }
    }
}

