/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.StaffDao;
import net.evecom.common.usms.uma.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 描述
     * 查询网格员列表
     * @return
     * @param officalPost
     */
    @Override
    public List<UserEntity> findUsersByOfficalPost(String officalPost) {
        return staffDao.findUsersByOfficalPost(officalPost);
    }

}
