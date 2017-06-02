/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.StaffDao;
import net.evecom.common.usms.uma.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 10:31
 */
@Transactional
@Service
public class StaffServiceImpl extends BaseServiceImpl<StaffEntity, Long>
        implements StaffService {

    /**
     * 注入StaffDao
     */
    @Autowired
    private StaffDao staffDao;

    @Override
    public BaseDao<StaffEntity, Long> getBaseDao() {
        return staffDao;
    }

    /**
     * 描述
     * 查询网格员列表
     *
     * @param officalPost
     * @return
     */
    @Override
    public List<UserEntity> findUsersByOfficalPost(String officalPost) {
        return staffDao.findUsersByOfficalPost(officalPost);
    }

}
