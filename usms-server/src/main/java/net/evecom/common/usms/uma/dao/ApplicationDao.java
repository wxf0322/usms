/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述 应用Dao层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:33
 */
public interface ApplicationDao {
    /**
     * 创建 Application
     *
     * @param applicationEntity
     * @return
     */
    ApplicationEntity createApplication(ApplicationEntity applicationEntity);

    /**
     * 更新 Application
     *
     * @param applicationEntity
     * @return
     */
    ApplicationEntity updateApplication(ApplicationEntity applicationEntity);

    /**
     * 根据 id 删除 Application
     *
     * @param id
     */
    void deleteApplication(Long id);

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    ApplicationEntity findOne(Long id);

    /**
     * 查询全部
     *
     * @return
     */
    List<ApplicationEntity> findAll();

    /**
     * 根据ClientId查找对象
     *
     * @param clientId
     * @return
     */
    ApplicationEntity findByClientId(String clientId);

    /**
     * 根据ClientSecret查找对象
     *
     * @param clientSecret
     * @return
     */
    ApplicationEntity findByClientSecret(String clientSecret);

    /**
     * 根据应用编码查询用户列表
     *
     * @param appName
     * @return
     */
    List<UserEntity> getUsersByApplicationName(String appName);
}
