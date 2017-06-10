/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.model.OperationModel;
import net.evecom.common.usms.uma.service.PrivilegeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
     * 注入privilegeService
     */
    @Autowired
    private PrivilegeService privilegeService;

    @ResponseBody
    @RequestMapping(value = "list")
    public Page<PrivilegeEntity> list(Integer page, Integer size, HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        if (!StringUtils.isEmpty(request.getParameter("key"))) {
            sqlFilter.addOrFilter("QUERY_p#label_S_LK", request.getParameter("key"));
            sqlFilter.addOrFilter("QUERY_p#name_S_LK", request.getParameter("key"));
        }
        return privilegeService.findByPage(page, size,sqlFilter);
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
    public PrivilegeEntity saveOrUpdate(@RequestBody PrivilegeEntity privilegeEntity) {
        return privilegeService.saveOrUpdate(privilegeEntity);
    }

    @ResponseBody
    @RequestMapping(value = "operations/update")
    public ResultStatus updateOperations(Long privilegeId, String operationIds) {
        String[] operationIdArray;
        if (StringUtils.isEmpty(operationIds)) {
            operationIdArray = null;
        } else {
            operationIdArray = operationIds.split(",");
        }
        privilegeService.updateOperations(privilegeId, operationIdArray);
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "operations")
    public List<OperationModel> findOperationsByPrivId(Long privilegeId) {
        return privilegeService.findOperationsByPrivId(privilegeId);
    }

}
