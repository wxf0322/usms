/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 描述 应用Dao层实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:34
 */
@Repository
public class ApplicationDaoImpl extends BaseDaoImpl<ApplicationEntity, Long>
        implements ApplicationDao {

    @Override
    public Page<ApplicationEntity> listAppsByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_applications a " + sqlFilter.getWhereSql());
        String sql = sb.toString();
        return super.queryForClass(sql, sqlFilter.getParams().toArray(), page, size);
    }

    @Override
    public ApplicationEntity getAppByClientId(String clientId) {
        List<ApplicationEntity> result = super.namedQueryForClass("Application.getAppByClientId",
                new Object[]{clientId});
        return JpaUtil.getSingleResult(result);
    }

    @Override
    public ApplicationEntity getAppByClientSecret(String clientSecret) {
        List<ApplicationEntity> result = super.namedQueryForClass("Application.getAppByClientSecret",
                new Object[]{clientSecret});
        return JpaUtil.getSingleResult(result);
    }

    /**
     * 根据应用编码查询用户列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByAppName(String appName) {
        List<UserEntity> result = super.namedQueryForClass("Application.listUsersByAppName",
                new Object[]{appName});
        return result;
    }

}
