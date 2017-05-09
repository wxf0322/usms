/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 9:59
 */
public interface StaffDao {

    StaffEntity findOne(Long id);

    /**
     * 描述
     * 查询网格员列表
     * @return
     * @param officalPost
     */
    List<UserEntity> findUsersByOfficalPost(String officalPost);

}
