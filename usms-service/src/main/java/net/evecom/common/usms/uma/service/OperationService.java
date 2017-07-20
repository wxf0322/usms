/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.entity.OperationEntity;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:45
 */

public interface OperationService extends BaseService<OperationEntity, Long> {
    /**
     * 获取应用操作列表
     *
     * @param appName
     * @return
     */
    List<OperationEntity> listOpersByAppName(String appName);

    /**
     * 通过 userId 查找操作
     *
     * @param userId
     * @return
     */
    List<OperationEntity> listOpersByUserId(Long userId);

    /**
     * 判断是否拥有该操作
     *
     * @param userId
     * @param operName
     * @return
     */
    boolean hasOperation(long userId, String operName);

    /**
     * 检查该操作节点是否能被删除
     *
     * @param id
     * @return
     */
    boolean canBeDeleted(Long id);

}
