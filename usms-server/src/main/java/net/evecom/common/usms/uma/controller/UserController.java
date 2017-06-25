/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.vo.ResultStatus;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.vo.GridVO;
import net.evecom.common.usms.vo.UserVO;
import net.evecom.common.usms.uma.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @ResponseBody
    @RequestMapping(value = "list")
    public Page<UserVO> listUsersByPage(Integer page, Integer size, Long institutionId, HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        String key = request.getParameter("key");
        if (!StringUtils.isEmpty(key)) {
            sqlFilter.addOrFilter("QUERY_u#login_name_S_LK", key);
            sqlFilter.addOrFilter("QUERY_u#name_S_LK", key);
        }
        return userService.listUsersByPage(page, size, institutionId, sqlFilter);
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
    public UserVO findOne(Long id) {
        UserEntity user = userService.findOne(id);
        UserVO userVO = new UserVO(user);
        return userVO;
    }

    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public ResultStatus saveOrUpdate(@RequestBody UserVO userVO, HttpServletRequest request) {
        String institutionId = request.getParameter("institutionId");
        String[] roleIdArray;
        if (StringUtils.isEmpty(userVO.getRoleIds())) {
            roleIdArray = null;
        } else {
            roleIdArray = userVO.getRoleIds().split(",");
        }
        if (userVO.getId() == null) {
            UserEntity userEntity = userService.createUser(userVO);
            if (!StringUtils.isEmpty(institutionId)) {
                userService.createUserInstitution(userEntity.getId(), Long.valueOf(institutionId));
            }
            userService.updateRoles(userEntity.getId(), roleIdArray);
        } else {
            userService.updateUser(userVO);
            userService.updateRoles(userVO.getId(), roleIdArray);
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
        String[] ids;
        if (StringUtils.isEmpty(institutionIds)) {
            ids = null;
        } else {
            ids = institutionIds.split(",");
        }
        userService.updateInstitutions(userId, ids);
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "institutions")
    public List<InstitutionEntity> findInstByUserId(Long userId) {
        return userService.listInstsByUserId(userId);
    }

    @ResponseBody
    @RequestMapping(value = "grids")
    public List<GridVO> listGridsByUserId(Long userId) {
        UserEntity user = userService.findOne(userId);
        String loginName = user.getLoginName();
        List<GridEntity> grids = userService.listGridsByLoginName(loginName);
        List<GridVO> result = new ArrayList<>();
        for (GridEntity grid : grids) {
            GridVO gridVO = new GridVO(grid);
            result.add(gridVO);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "roles/target")
    public List<RoleEntity> listTargetRoles(Long userId) {
        return userService.listTargetRoles(userId);
    }

    @ResponseBody
    @RequestMapping(value = "roles/source")
    public List<RoleEntity> listSourceRoles(Long userId) {
        return userService.listSourceRoles(userId);
    }

}
