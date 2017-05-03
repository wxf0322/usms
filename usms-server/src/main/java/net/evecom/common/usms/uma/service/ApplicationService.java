/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;


import net.evecom.common.usms.entity.ApplicationEntity;

import java.util.List;

/**
 * 描述 应用Service层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:37
 */
public interface ApplicationService {

    ApplicationEntity createApplication(ApplicationEntity application);

    ApplicationEntity updateApplication(ApplicationEntity application);

    void deleteApplication(Long id);

    ApplicationEntity findOne(Long id);

    List<ApplicationEntity> findAll();

    ApplicationEntity findByClientId(String clientId);

    ApplicationEntity findByClientSecret(String clientSecret);

}
