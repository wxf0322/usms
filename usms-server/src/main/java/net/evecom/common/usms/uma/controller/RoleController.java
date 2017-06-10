/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.RoleModel;
import net.evecom.common.usms.uma.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
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
     * 日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);



    /**
     * 查找角色列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list")
    public Page<RoleEntity> list(Integer page, Integer size, HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        if (!org.springframework.util.StringUtils.isEmpty(request.getParameter("key"))) {
            sqlFilter.addOrFilter("QUERY_r#label_S_LK", request.getParameter("key"));
            sqlFilter.addOrFilter("QUERY_r#name_S_LK", request.getParameter("key"));
        }
        return roleService.findByPage(page, size,sqlFilter);
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
    public ResultStatus saveOrUpdate(@RequestBody RoleModel roleModel) {
        RoleEntity roleEntity = null;
        try {
            roleEntity = new RoleEntity(roleModel);
            roleEntity = roleService.saveOrUpdate(roleEntity);
            String[] privilegeIdArray;
            if (StringUtils.isEmpty(roleModel.getPrivilegeIds())) {
                privilegeIdArray = null;
            } else {
                privilegeIdArray = roleModel.getPrivilegeIds().split(",");
            }
            roleService.updatePrivileges(roleEntity.getId(), privilegeIdArray);
            String[] userIdArray;
            if (StringUtils.isEmpty(roleModel.getUserIds())) {
                userIdArray = null;
            } else {
                userIdArray = roleModel.getUserIds().split(",");
            }
            roleService.updateUsers(roleEntity.getId(), userIdArray);
        } catch (InvocationTargetException |IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
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



}
