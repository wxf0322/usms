/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.uma.dao.custom.ApplicationDaoCustom;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * 描述 拓展类实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午5:02
 */
@Repository
public class ApplicationDaoImpl extends BaseDaoImpl<ApplicationEntity> implements ApplicationDaoCustom {
    @Override
    public Page<ApplicationEntity> listAppsByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_applications a ").append(sqlFilter.getWhereSql());
        String sql = sb.toString();
        return super.queryForClass(sql, sqlFilter.getParams().toArray(), page, size);
    }
}
