/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import net.evecom.common.usms.uma.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 描述 应用Service层实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:37
 */
@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationService {

    /**
     * 注入Dao层
     */
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public ApplicationEntity createApplication(ApplicationEntity application) {
        application.setClientId(UUID.randomUUID().toString());
        application.setClientSecret(UUID.randomUUID().toString());
        return applicationDao.createApplication(application);
    }

    @Override
    public ApplicationEntity updateApplication(ApplicationEntity application) {
        return applicationDao.updateApplication(application);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationDao.deleteApplication(id);
    }

    @Override
    public ApplicationEntity findOne(Long id) {
        return applicationDao.findOne(id);
    }

    @Override
    public List<ApplicationEntity> findAll() {
        return applicationDao.findAll();
    }

    @Override
    public ApplicationEntity findByClientId(String clientId) {
        return applicationDao.findByClientId(clientId);
    }

    @Override
    public ApplicationEntity findByClientSecret(String clientSecret) {
        return applicationDao.findByClientSecret(clientSecret);
    }

    /**
     * 根据应用编码查询用户列表
     * @param appName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByApplicationName(String appName) {
        return applicationDao.getUsersByApplicationName(appName);
    }
}
