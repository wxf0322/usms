/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 17:34
 */
public interface PrivilegeService {
    List<PrivilegeEntity> getPrivilegesByAppName(String application);
    List<PrivilegeEntity> getPrivilegesByUserId(long userID);
    boolean hasPrivilege(long userID,String privilegeName);


    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    List<UserEntity> getUsersByPrivName(String privName);

}
