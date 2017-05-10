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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:34
 */
@Transactional
@Service
public class PrivilegeServiceImpl implements PrivilegeService{

    /**
     * privilegeDao的注入
     */
    @Autowired
    private PrivilegeDao privilegeDao;

    /**
     * 根据app名称来获取权限列表
     * @return
     * @param
     */
    @Override
    public List<PrivilegeEntity> getPrivilegesByAppName(String application) {
        return privilegeDao.getPrivilegesByAppName(application);
    }

    /**
     * 根据用户编码来获取权限列表
     * @return
     * @param  userID
     */
    @Override
    public List<PrivilegeEntity> getPrivilegesByUserId(long userID) {
        return privilegeDao.getPrivilegesByUserId(userID);
    }

    /**
     * 判断是否拥有该权限
     * @return
     * @param userID,privilegeName
     */
    @Override
    public boolean hasPrivilege(long userID, String privilegeName) {
        return privilegeDao.hasPrivilege(userID,privilegeName);
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
