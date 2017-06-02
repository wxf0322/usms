/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.UserModel;
import net.evecom.common.usms.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @RequestMapping(value = "list")
    public Page<UserModel> list(Integer page, Integer size) {
        return userService.findModelsByPage(page, size);
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
    public ResultStatus saveOrUpdate(@RequestBody UserModel userModel) {
        if (userModel.getId() == null) {
            userService.createUser(userModel);
        } else {
            userService.updateUser(userModel);
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "password/reset", method = RequestMethod.POST)
    public ResultStatus resetPassword(Long id) {
        String newPassword = "123456";
        userService.changePassword(id, newPassword);
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
