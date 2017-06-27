/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-6-26 15:59
 */
public interface UserJpa extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findByLoginName(String loginName);
}
