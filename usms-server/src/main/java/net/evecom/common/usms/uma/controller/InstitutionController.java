/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.core.service.TreeService;
import net.evecom.common.usms.core.util.BeanUtil;
import net.evecom.common.usms.core.util.MapUtil;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.model.InstitutionModel;
import net.evecom.common.usms.model.TreeDataModel;
import net.evecom.common.usms.uma.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 描述 组织机构管理Controller层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/17 11:03
 */
@Controller
@RequestMapping("/institution")
public class InstitutionController {

    /**
     * 注入日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(InstitutionController.class);

    /**
     * 注入InstitutionService
     */
    @Autowired
    private InstitutionService institutionService;

    /**
     * 注入TreeService
     */
    @Autowired
    private TreeService treeService;

    @ResponseBody
    @RequestMapping(value = "tree")
    public List<TreeDataModel> findAllTreeData() {
        return treeService.findAllTreeData("usms_institutions");
    }

    @ResponseBody
    @RequestMapping(value = "find")
    public InstitutionModel findOne(Long id) {
        InstitutionEntity institutionEntity = institutionService.findOne(id);
        InstitutionModel institutionModel = new InstitutionModel(institutionEntity);
        return institutionModel;
    }

    @ResponseBody
    @RequestMapping(value = "saveOrUpdate")
    public ResultStatus saveOrUpdate(@RequestBody InstitutionModel institutionModel) {
        Long entityId = institutionModel.getId();
        Long parentId = institutionModel.getParentId();
        try {
            Map<String, Object> underlineMap = MapUtil.toUnderlineStringMap(MapUtil.toMap(institutionModel));
            this.treeService.saveOrUpdateTreeData(entityId, parentId, underlineMap,
                    "usms_institutions",
                    "usms_institutions_seq");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return new ResultStatus(false, "");
        }
        return new ResultStatus(true, "");
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public ResultStatus delete(Long id) {
        institutionService.delete(id);
        return new ResultStatus(true, "");
    }

}
