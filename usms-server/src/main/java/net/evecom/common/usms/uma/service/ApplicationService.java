/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;


import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述 应用Service层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:37
 */
public interface ApplicationService extends BaseService<ApplicationEntity, Long> {

    /**
     * 查询应用列表
     */
    Page<ApplicationEntity> listAppsByPage(int page, int size, SqlFilter sqlFilter);

    /**
     * 创建应用
     *
     * @param application
     * @return
     */
    ApplicationEntity createApplication(ApplicationEntity application);

    /**
     * 更新应用
     *
     * @param application
     * @return
     */
    ApplicationEntity updateApplication(ApplicationEntity application);

    /**
     * 根据ClientId查询单个
     *
     * @param clientId
     * @return
     */
    ApplicationEntity getAppByClientId(String clientId);

    /**
     * 根据ClientSecret查询单个
     *
     * @param clientSecret
     * @return
     */
    ApplicationEntity getAppByClientSecret(String clientSecret);

    /**
     * 通过 Id 查找应用
     *
     * @param id
     * @return
     */
    List<ApplicationEntity> listAppsByUserId(Long userId);

}
