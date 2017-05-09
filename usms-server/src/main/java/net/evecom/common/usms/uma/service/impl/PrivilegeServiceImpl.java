/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import net.evecom.common.usms.uma.service.PrivilegeService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 17:34
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService{

    @Resource
    private PrivilegeDao privilegeDao;

    @Override
    public List<PrivilegeEntity> getPrivilegesByAppName(String application) {
        return privilegeDao.getPrivilegesByAppName(application);
    }

    @Override
    public List<PrivilegeEntity> getPrivilegesByUserId(long userID) {
        return privilegeDao.getPrivilegesByUserId(userID);
    }

    /**
     * 根据权限编码查询用户列表
     * @param privName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByPrivName(String privName) {
        return privilegeDao.getUsersByPrivName(privName);
    }
}
