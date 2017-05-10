/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:26
 */
public interface PrivilegeDao {

    /**
     * 根据app名字获取权限列表
     * @param application
     * @return
     */
    List<PrivilegeEntity> getPrivilegesByAppName(String application);

    /**
     * 根据用户名获取权限列表
     * @param userID
     * @return
     */
    List<PrivilegeEntity> getPrivilegesByUserId(long userID);

    /**
     * 判断是否拥有次权限
     * @param userID,privilegeName
     * @return
     */
    boolean hasPrivilege(long userID,String privilegeName);

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    List<UserEntity> getUsersByPrivName(String privName);
}
