/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 15:16
 */
public interface InstitutionService {

    /**
     * 根据登陆名获取机构列表
     *
     * @param loginName
     * @return
     */
    List<InstitutionEntity> findInstByLoginName(String loginName);

    /**
     * 根据登陆名获取机构列表
     *
     * @param name
     * @return
     */
    InstitutionEntity getByName(String name);

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    List<UserEntity> getUsersByInstName(String instName);
}
