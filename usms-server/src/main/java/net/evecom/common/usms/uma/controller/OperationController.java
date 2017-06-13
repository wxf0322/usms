/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.core.service.TreeService;
import net.evecom.common.usms.core.util.MapUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.model.OperationModel;
import net.evecom.common.usms.core.model.TreeDataModel;
import net.evecom.common.usms.uma.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-23 10:32
 */
@Controller
@RequestMapping("/operation")
public class OperationController {
    /**
     * 注入日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(OperationController.class);

    /**
     * 注入operationService
     */
    @Autowired
    private OperationService operationService;

    /**
     * 注入TreeService
     */
    @Autowired
    private TreeService treeService;

    @ResponseBody
    @RequestMapping(value = "tree")
    public List<TreeDataModel> findTreeData(HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        if(!StringUtils.isEmpty(request.getParameter("applicationId"))){
            sqlFilter.addFilter("QUERY_o#application_id_L_EQ",request.getParameter("applicationId"));
        }
        return treeService.findTreeData("usms_operations o",sqlFilter);
    }

    @ResponseBody
    @RequestMapping(value = "find")
    public OperationModel findOne(Long id) {
        OperationEntity operationEntity = operationService.findOne(id);
        OperationModel operationModel = new OperationModel(operationEntity);
        return operationModel;
    }

    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public ResultStatus saveOrUpdate(@RequestBody OperationModel operationModel) {
        Long entityId = operationModel.getId();
        Long parentId = operationModel.getParentId();
        try {
            Map<String, Object> underlineMap = MapUtil.toUnderlineStringMap(MapUtil.toMap(operationModel));
            treeService.saveOrUpdateTreeData(entityId, parentId, underlineMap,
                    "usms_operations", "usms_operations_s");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return new ResultStatus(false, "");
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public ResultStatus delete(Long id) {
        operationService.delete(id);
        return new ResultStatus(true, "");
    }


}
