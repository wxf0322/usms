/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 14:45
 */

public interface OperationService {
    /**
     * 获取应用操作列表
     * @param application
     * @return
     */
    List<OperationEntity> getOperationsByAppName(String application);

    /**
     * 根据操作编码查询用户列表
     *
     * @param operName
     * @return
     */
    List<UserEntity> getUsersByOperName(String operName);

    /**
     * 获取某个应用的操作
     * @param application
     * @return
     *//*
    List<OperationEntity> getOperation(String application);*/
}
