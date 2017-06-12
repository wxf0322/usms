/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.UserModel;
import net.evecom.common.usms.uma.service.InstitutionService;
import net.evecom.common.usms.uma.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 user控制器
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/25 8:50
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 注入UserService
     */
    @Autowired
    private UserService userService;

    @Autowired
    private InstitutionService institutionService;

    @ResponseBody
    @RequestMapping(value = "list")
    public Page<UserModel> list(Integer page, Integer size, HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        if (!StringUtils.isEmpty(request.getParameter("key"))) {
            sqlFilter.addOrFilter("QUERY_u#login_name_S_LK", request.getParameter("key"));
            sqlFilter.addOrFilter("QUERY_u#name_S_LK", request.getParameter("key"));
        }
        if (!StringUtils.isEmpty(request.getParameter("institutionName"))) {
            List<UserEntity> entitiesList =
                    institutionService.findUsersByInstName(request.getParameter("institutionName"));
            List<UserModel> modelsList = new ArrayList<>();
            for (UserEntity userEntity : entitiesList) {
                UserModel userModel = new UserModel(userEntity);
                modelsList.add(userModel);
            }
            return new PageImpl<>(modelsList, new PageRequest(page, size), modelsList.size());
        }
        return userService.findModelsByPage(page, size, sqlFilter);
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public ResultStatus delete(String columns) {
        String[] ids = columns.split(",");
        for (String id : ids) {
            userService.delete(Long.valueOf(id));
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "find")
    public UserModel findOne(Long id) {
        UserEntity user = userService.findOne(id);
        UserModel userModel = new UserModel(user);
        return userModel;
    }

    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public ResultStatus saveOrUpdate(@RequestBody UserModel userModel, HttpServletRequest request) {
        String institutionId = request.getParameter("institutionId");
        if (userModel.getId() == null) {
            UserEntity userEntity = userService.createUser(userModel);
            if (!StringUtils.isEmpty(institutionId)) {
                userService.createUserInstitution(userEntity.getId(), Long.valueOf(institutionId));
            }
        } else {
            userService.updateUser(userModel);
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "password/reset", method = RequestMethod.POST)
    public ResultStatus resetPassword(String ids) {
        String[] idArray = ids.split(",");
        String newPassword = "123456";
        for (String id : idArray) {
            userService.changePassword(Long.valueOf(id), newPassword);
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "institutions/update")
    public ResultStatus updateInstitutions(Long userId, String institutionIds) {
        String[] institutionIdArray;
        if (StringUtils.isEmpty(institutionIds)) {
            institutionIdArray = null;
        } else {
            institutionIdArray = institutionIds.split(",");
        }
        userService.updateInstitutions(userId, institutionIdArray);
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "institutions")
    public List<InstitutionEntity> findInstByUserId(Long userId) {
        return userService.findInstByUserId(userId);
    }

}
