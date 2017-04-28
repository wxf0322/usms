/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.service.impl;

import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.oauth2.dao.StaffDao;
import net.evecom.common.usms.oauth2.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 10:31
 */
@Service
public class StaffServiceImpl implements StaffService {

    /**
     * 注入StaffDao
     */
    @Autowired
    private StaffDao staffDao;

    @Override
    public StaffEntity findOne(Long id) {
        return staffDao.findOne(id);
    }
}
