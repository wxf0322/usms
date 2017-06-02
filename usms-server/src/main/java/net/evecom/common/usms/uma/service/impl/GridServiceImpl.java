/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.GridDao;
import net.evecom.common.usms.uma.service.GridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述
 *
 * @author Arno Chen
 * @version 1.0
 * @created 2017/5/8 15:58
 */
@Transactional
@Service
public class GridServiceImpl implements GridService {
    /**
     * 注入GridDao
     */
    @Autowired
    private GridDao gridDao;

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridCode
     * @return
     */
    @Override
    public List<UserEntity> getUsersByGridCode(String gridCode) {
        return gridDao.getUsersByGridCode(gridCode);
    }
}
