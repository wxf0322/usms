/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 18:28
 */
public interface RoleService {
    List<RoleEntity> findRolesByUserId(long userID);

    /**
     * 根据角色编码查询用户列表
     *
     * @param roleName
     * @return
     */
    List<UserEntity> getUsersByRoleName(String roleName);

}
