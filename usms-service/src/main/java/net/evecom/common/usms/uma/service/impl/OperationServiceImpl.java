/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.uma.dao.OperationDao;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import net.evecom.common.usms.uma.service.OperationService;
import net.evecom.common.usms.vo.OperationVO;
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

    /**
     * 判断该操作节点能否被删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean canBeDeleted(Long id) {
        return operationDao.canBeDeleted(id);
    }

    @Override
    public List<OperationVO> listOpersByPrivId(Long privilegeId) {
        return operationDao.listOpersByPrivId(privilegeId);
    }


}
