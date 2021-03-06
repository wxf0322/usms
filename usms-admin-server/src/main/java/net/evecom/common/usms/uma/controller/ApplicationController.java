/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.vo.ResultStatus;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.uma.service.ApplicationService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 操作相关Service层
 *
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
    public Page<ApplicationEntity> listAppsByPage(Integer page, Integer size, HttpServletRequest request) {
        SqlFilter sqlFilter = new SqlFilter();
        if (!StringUtils.isEmpty(request.getParameter("key"))) {
            sqlFilter.addOrFilter("QUERY_a#label_S_LK", request.getParameter("key"));
            sqlFilter.addOrFilter("QUERY_a#name_S_LK", request.getParameter("key"));
        }
        return applicationService.listAppsByPage(page, size,sqlFilter);
    }

    @ResponseBody
    @RequestMapping(value = "all")
    public List<ApplicationEntity> findAll(){
        return applicationService.findAll();
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
