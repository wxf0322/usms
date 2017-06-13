/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.controller;

import net.evecom.common.usms.core.model.TreeDataModel;
import net.evecom.common.usms.uma.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 描述 管辖区域管理Controller层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/17 11:03
 */
@Controller
@RequestMapping("/grid")
public class GridController {

    /**
     * 注入GridService
     */
    @Autowired
    private GridService gridService;

    /**
     * 返回树形节点数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "tree")
    public List<TreeDataModel> findTreeData() {
        return gridService.findTreeData();
    }

}
