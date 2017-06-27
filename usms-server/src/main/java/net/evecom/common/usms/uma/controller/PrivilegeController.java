/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.vo.ResultStatus;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.vo.OperationVO;
import net.evecom.common.usms.vo.PrivilegeVO;
import net.evecom.common.usms.uma.service.PrivilegeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-19 16:37
 */
@Controller
@RequestMapping("/privilege")
public class PrivilegeController {

    /**
     * 日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    /**
     * 注入privilegeService
     */
    @Autowired
    private PrivilegeService privilegeService;

    @ResponseBody
    @RequestMapping(value = "list")
    public Page<PrivilegeEntity> listPrivsByPage(Integer page, Integer size, HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        if (!StringUtils.isEmpty(request.getParameter("key"))) {
            sqlFilter.addOrFilter("QUERY_p#label_S_LK", request.getParameter("key"));
            sqlFilter.addOrFilter("QUERY_p#name_S_LK", request.getParameter("key"));
        }
        return privilegeService.listPrivsByPage(page, size,sqlFilter);
    }

    @ResponseBody
    @RequestMapping(value = "find")
    public PrivilegeEntity findOne(Long id) {
        return privilegeService.findOne(id);
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public ResultStatus delete(String columns) {
        String[] ids = columns.split(",");
        for (String id : ids) {
            privilegeService.delete(Long.valueOf(id));
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public PrivilegeEntity saveOrUpdate(@RequestBody PrivilegeVO privilegeVO) {
        PrivilegeEntity privilegeEntity = null;
        try {
            privilegeEntity = new PrivilegeEntity(privilegeVO);
            privilegeEntity = privilegeService.saveOrUpdate(privilegeEntity);
            String[] roleIds;
            if (StringUtils.isEmpty(privilegeVO.getRoleIds())) {
                roleIds = null;
            } else {
                roleIds = privilegeVO.getRoleIds().split(",");
            }
            privilegeService.updateRoles(privilegeEntity.getId(), roleIds);
        } catch (InvocationTargetException |IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }
        return privilegeEntity;
    }

    @ResponseBody
    @RequestMapping(value = "operations/update")
    public ResultStatus updateOperations(Long privilegeId, String operationIds) {
        String[] ids;
        if (StringUtils.isEmpty(operationIds)) {
            ids = null;
        } else {
            ids = operationIds.split(",");
        }
        privilegeService.updateOperations(privilegeId, ids);
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "operations")
    public List<OperationVO> listOpersByPrivId(Long privilegeId) {
        return privilegeService.listOpersByPrivId(privilegeId);
    }

    @ResponseBody
    @RequestMapping(value = "roles/target")
    public List<RoleEntity> listTargetRoles(Long privilegeId){
         return privilegeService.listTargetRoles(privilegeId);
    }

    @ResponseBody
    @RequestMapping(value = "roles/source")
    public  List<RoleEntity> listSourceRoles(Long privilegeId){
        return privilegeService.listSourceRoles(privilegeId);
    }

}
