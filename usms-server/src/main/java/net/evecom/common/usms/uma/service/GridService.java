/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述
 *
 * @author Arno Chen
 * @version 1.0
 * @created 2017/5/8 15:58
 */
public interface GridService extends BaseService<GridEntity, Long> {

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridCode
     * @return
     */
    List<UserEntity> findUsersByGridCode(String gridCode);
}
