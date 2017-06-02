/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-17 15:38
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    /**
     * 注入roleService
     */
    @Autowired
    private RoleService roleService;

    /**
     * 查找角色列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public Page<RoleEntity> list(Integer page, Integer size) {
        return roleService.findByPage(page, size);
    }

    @ResponseBody
    @RequestMapping(value = "find")
    public RoleEntity findOne(Long id) {
        return roleService.findOne(id);
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public ResultStatus delete(String columns) {
        String[] ids = columns.split(",");
        for (String id : ids) {
            roleService.delete(Long.valueOf(id));
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public ResultStatus saveOrUpdate(@RequestBody RoleEntity roleEntity) {
        roleService.saveOrUpdate(roleEntity);
        return new ResultStatus(true, "");
    }

    /**
     * 查找已选用户列表
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "privileges/selected")
    public List<PrivilegeEntity> getSelectedPrivileges(Long roleId) {
        return roleService.getSelectedPrivileges(roleId);
    }

    /**
     * 查找角色Id对应的未选择权限列表
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "privileges/unselected")
    public List<PrivilegeEntity> getUnselectedPrivileges(Long roleId) {
        return roleService.getUnselectedPrivileges(roleId);
    }

    /**
     * 更新角色对应的权限列表
     * @param roleId
     * @param privilegeIds
     */
    @ResponseBody
    @RequestMapping(value = "privileges/update")
    public ResultStatus updatePrivileges(Long roleId, String privilegeIds) {
        String[] privilegeIdArray;
        if (StringUtils.isEmpty(privilegeIds)) {
            privilegeIdArray = null;
        } else {
            privilegeIdArray = privilegeIds.split(",");
        }
        roleService.updatePrivileges(roleId, privilegeIdArray);
        return new ResultStatus(true, "");
    }

    /**
     * 根据角色ID查找已选用户列表
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "users/selected")
    public List<Map<String, Object>> getSelectedUsers(Long roleId) {
        return roleService.getSelectedUsers(roleId);
    }

    /**
     * 根据角色ID查找未选用户列表
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "users/unselected")
    public List<Map<String, Object>> getUnselectedUsers(Long roleId) {
        return roleService.getUnselectedUsers(roleId);
    }

    /**
     * 更新用户列表
     *
     * @param roleId
     * @param userIds
     */
    @ResponseBody
    @RequestMapping(value = "users/update")
    public ResultStatus updateUsers(Long roleId, String userIds) {
        String[] userIdArray;
        if (StringUtils.isEmpty(userIds)) {
            userIdArray = null;
        } else {
            userIdArray = userIds.split(",");
        }
        roleService.updateUsers(roleId, userIdArray);
        return new ResultStatus(true, "");
    }

}
