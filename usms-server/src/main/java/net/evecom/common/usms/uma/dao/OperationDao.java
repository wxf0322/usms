/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:20
 */
public interface OperationDao extends BaseDao<OperationEntity, Long> {

    /**
     * 获取应用操作列表
     *
     * @param appName
     * @return
     */
    List<OperationEntity> listOpersByAppName(String appName);

    /**
     * 根据操作编码查询用户列表
     *
     * @param operName
     * @return
     */
    List<UserEntity> listUsersByOperName(String operName);

    /**
     * 判断是否拥有该操作
     *
     * @param userId
     * @param operationName
     * @return
     */
    boolean hasOperation(long userId, String operName);

}
