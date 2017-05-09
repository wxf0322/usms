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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 14:44
 */
@Transactional
@Service
public class OperationServiceImpl implements OperationService {

    /**
     * OperationDao的注入
     */
    @Resource
    private OperationDao operationDao;

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

   /* @Override
    public List<OperationEntity> getOperation(String operation) {
        return operationDao.findOperation(operation);
    }*/
}
