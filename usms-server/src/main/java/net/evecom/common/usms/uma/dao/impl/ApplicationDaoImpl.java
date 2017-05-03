/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 描述 应用Dao层实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:34
 */
@Repository
public class ApplicationDaoImpl implements ApplicationDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public ApplicationEntity createApplication(final ApplicationEntity applicationEntity) {
        return JpaUtil.saveOrUpdate(applicationEntity.getId(), manager, applicationEntity);
    }

    @Override
    public ApplicationEntity updateApplication(ApplicationEntity applicationEntity) {
        return JpaUtil.saveOrUpdate(applicationEntity.getId(), manager, applicationEntity);
    }

    @Override
    public void deleteApplication(Long id) {
        ApplicationEntity found = findOne(id);
        if (found != null) {
            manager.remove(found);
        } else {
            throw new IllegalArgumentException("ApplicationEntity not found: This model ID is " + id);
        }
    }

    @Override
    public ApplicationEntity findOne(Long id) {
        return manager.find(ApplicationEntity.class, id);
    }

    @Override
    public List<ApplicationEntity> findAll() {
        TypedQuery<ApplicationEntity> query = manager.createNamedQuery(ApplicationEntity.QUERY_ALL,
                ApplicationEntity.class);
        return query.getResultList();
    }

    @Override
    public ApplicationEntity findByClientId(String clientId) {
        TypedQuery<ApplicationEntity> query = manager.createNamedQuery(ApplicationEntity.QUERY_BY_CLIENT_ID,
                ApplicationEntity.class);
        query.setParameter(ApplicationEntity.PARAM_CLIENT_ID, clientId);
        return JpaUtil.getSingleResult(query.getResultList());
    }

    @Override
    public ApplicationEntity findByClientSecret(String clientSecret) {
        TypedQuery<ApplicationEntity> query = manager.createNamedQuery(ApplicationEntity.QUERY_BY_CLIENT_SECRET,
                ApplicationEntity.class);
        query.setParameter(ApplicationEntity.PARAM_CLIENT_SECRET, clientSecret);
        return JpaUtil.getSingleResult(query.getResultList());
    }
}
