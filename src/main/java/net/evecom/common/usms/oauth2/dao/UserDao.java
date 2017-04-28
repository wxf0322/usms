/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.dao;

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述 UserDao
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:46
 */
public interface UserDao {

    UserEntity createUser(UserEntity user);

    UserEntity updateUser(UserEntity user);

    void deleteUser(Long id);

    UserEntity findOne(Long id);

    List<UserEntity> findAll();

    UserEntity findByLoginName(String loginName);

    List<OperationEntity> findOperationsById(Long id);

    List<ApplicationEntity> findApplicationsBy(Long id);
}
