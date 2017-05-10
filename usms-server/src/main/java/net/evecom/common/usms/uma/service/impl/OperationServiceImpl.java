/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.OperationDao;
import net.evecom.common.usms.uma.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:44
 */
@Transactional
@Service
public class OperationServiceImpl implements OperationService {

    /**
     * OperationDao的注入
     */
    @Autowired
    private OperationDao operationDao;

    /**
     * 获取应用操作列表
     * @param application
     * @return
     */
    @Override
    public List<OperationEntity> getOperationsByAppName(String application) {
        return operationDao.getOperationsByAppName(application);
    }

    /**
     * 根据操作编码查询用户列表
     * @param operName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByOperName(String operName) {
        return operationDao.getUsersByOperName(operName);
    }

    /**
     * 判断是否拥有该操作
     * @return
     * @param userID,operationName
     */
    @Override
    public boolean hasOperation(long userID,String operationName) {
        return operationDao.hasOperation(userID,operationName);
    }

}
