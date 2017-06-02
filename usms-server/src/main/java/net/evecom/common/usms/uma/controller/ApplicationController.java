/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.ResultStatus;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.uma.service.ApplicationService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-22 9:11
 */
@Controller
@RequestMapping("/application")
public class ApplicationController {
    /**
     * 注入applicationService
     */
    @Autowired
    private ApplicationService applicationService;

    @ResponseBody
    @RequestMapping(value = "list")
    public Page<ApplicationEntity> list(Integer page, Integer size) {
        return applicationService.findByPage(page, size);
    }

    @ResponseBody
    @RequestMapping(value = "find")
    public ApplicationEntity findOne(Long id) {
        return applicationService.findOne(id);
    }

    @ResponseBody
    @RequestMapping(value = "create")
    public ApplicationEntity create(@RequestBody ApplicationEntity applicationEntity) {
        return applicationService.createApplication(applicationEntity);
    }

    @ResponseBody
    @RequestMapping(value = "update")
    public ApplicationEntity update(@RequestBody ApplicationEntity applicationEntity) {
        return applicationService.updateApplication(applicationEntity);
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public ResultStatus delete(String columns) {
        if (StringUtils.isNotEmpty(columns)) {
            String[] ids = columns.split(",");
            long[] entityIds = Arrays.stream(ids).mapToLong(Long::valueOf).toArray();
            applicationService.delete(ArrayUtils.toObject(entityIds));
        }
        return new ResultStatus(true, "");
    }

}
