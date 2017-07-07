/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.custom;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import org.springframework.data.domain.Page;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午4:49
 */
public interface ApplicationDaoCustom extends BaseDao<ApplicationEntity> {

    /**
     * 查询权限列表
     *
     * @param page
     * @param size
     * @param sqlFilter
     * @return
     */
    Page<ApplicationEntity> listAppsByPage(int page, int size, SqlFilter sqlFilter);

}
