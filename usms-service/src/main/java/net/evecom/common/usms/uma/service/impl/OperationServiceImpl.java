/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.OperationDao;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:44
 */
@Transactional
@Service
public class OperationServiceImpl extends BaseServiceImpl<OperationEntity, Long>
        implements OperationService {

    /**
     * @see OperationDao
     */
    @Autowired
    private OperationDao operationDao;

    /**
     * @see UserDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 获取应用操作列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<OperationEntity> listOpersByAppName(String appName) {
        return operationDao.listOpersByAppName(appName);
    }

    /**
     * 通过id查找用户权限
     *
     * @param userId
     * @return
     */
    @Override
    public List<OperationEntity> listOpersByUserId(Long userId) {
        return operationDao.listOpersByUserId(userId);
    }

    /**
     * 判断是否拥有该操作
     *
     * @param userId
     * @param operName
     * @return
     */
    @Override
    public boolean hasOperation(long userId, String operName) {
        List<OperationEntity> result = operationDao.listUserOperations(userId, operName);
        return result.size() != 0;
    }

}