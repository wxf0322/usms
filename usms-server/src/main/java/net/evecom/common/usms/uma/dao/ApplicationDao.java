/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述 应用Dao层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:33
 */
public interface ApplicationDao extends BaseDao<ApplicationEntity, Long> {

    /**
     * 查询权限列表
     *
     * @param page
     * @param size
     * @param sqlFilter
     * @return
     */
    Page<ApplicationEntity> listAppsByPage(int page, int size, SqlFilter sqlFilter);

    /**
     * 根据ClientId查找对象
     *
     * @param clientId
     * @return
     */
    ApplicationEntity getAppByClientId(String clientId);

    /**
     * 根据ClientSecret查找对象
     *
     * @param clientSecret
     * @return
     */
    ApplicationEntity getAppByClientSecret(String clientSecret);

    /**
     * 根据应用编码查询用户列表
     *
     * @param appName
     * @return
     */
    List<UserEntity> listUsersByAppName(String appName);
}
