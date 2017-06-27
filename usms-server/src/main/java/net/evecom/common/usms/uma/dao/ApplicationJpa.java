/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 应用管理相关Jpa
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/26 上午8:59
 */
@Repository
public interface ApplicationJpa extends JpaRepository<ApplicationEntity, Long>{

    List<ApplicationEntity> findByClientId(String clientId);

    List<ApplicationEntity> findByClientSecret(String clientSecret);

}
