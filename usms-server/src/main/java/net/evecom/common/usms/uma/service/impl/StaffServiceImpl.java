/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.uma.dao.StaffDao;
import net.evecom.common.usms.uma.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述员工相关Service
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/29 下午1:05
 */
public class StaffServiceImpl extends BaseServiceImpl<StaffEntity, Long>
        implements StaffService{

    /**
     * 该类被上层Controller类调用，勿删
     * @see StaffDao
     */
    @Autowired
    private StaffDao staffDao;

}
