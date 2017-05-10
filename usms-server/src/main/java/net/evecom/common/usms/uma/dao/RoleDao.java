/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 18:24
 */
public interface RoleDao {

    /**
     * 根据用户名查找角色列表
     * @param userID
     * @return
     */
    List<RoleEntity> findRolesByUserId(long userID);

    /**
     * 判断是否拥有此角色
     * @param userID
     * @return
     */
    boolean hasRole(long userID,String RoleName);
    /**
     * 根据权限角色查询用户列表
     *
     * @param roleName
     * @return
     */
    List<UserEntity> getUsersByRoleName(String roleName);
}
