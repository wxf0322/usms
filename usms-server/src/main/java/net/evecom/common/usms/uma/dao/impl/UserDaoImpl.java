/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import net.evecom.common.usms.uma.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 UserDao实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:46
 */
@Repository
public class UserDaoImpl implements UserDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 注入ApplicationDao
     */
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public UserEntity createUser(final UserEntity user) {
        return JpaUtil.saveOrUpdate(user.getId(), manager, user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return JpaUtil.saveOrUpdate(user.getId(), manager, user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity found = findOne(id);
        if (found != null) {
            manager.remove(found);
        } else {
            throw new IllegalArgumentException("UserEntity not found: This model ID is " + id);
        }
    }

    @Override
    public UserEntity findOne(Long id) {
        return manager.find(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> findAll() {
        TypedQuery<UserEntity> query = manager.createNamedQuery(UserEntity.QUERY_ALL, UserEntity.class);
        return query.getResultList();
    }

    @Override
    public UserEntity findByLoginName(String loginName) {
        TypedQuery<UserEntity> query = manager.createNamedQuery(UserEntity.QUERY_BY_LOGIN_NAME, UserEntity.class);
        query.setParameter(UserEntity.PARAM_LOGIN_NAME, loginName);
        return JpaUtil.getSingleResult(query.getResultList());
    }

    @Override
    public List<OperationEntity> findOperationsById(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM usms_operations o\n")
                .append("WHERE o.id IN (SELECT po.oper_id\n")
                .append("  FROM usms_privilege_operation po\n")
                .append("  WHERE po.priv_id IN (SELECT p.id\n")
                .append("   FROM usms_privileges p\n")
                .append("   WHERE p.id IN (SELECT pr.priv_id\n")
                .append("     FROM usms_privilege_role pr\n")
                .append("     WHERE pr.role_id IN (SELECT ur.role_id\n")
                .append("      FROM usms_user_role ur\n")
                .append("      WHERE ur.user_id = :userId))\n")
                .append("    AND p.enabled = 1))\n")
                .append(" AND o.enabled = 1");

        String sql = sb.toString();

        Query query = manager.createNativeQuery(sql, OperationEntity.class);
        query.setParameter("userId", id);
        return query.getResultList();
    }

    public List<ApplicationEntity> findApplicationsById(Long id) {
        List<OperationEntity> operations = findOperationsById(id);
        long[] appIds = operations.stream().mapToLong(op -> op.getApplicationId()).distinct().toArray();
        List<ApplicationEntity> applications = new ArrayList<>();
        for(long appId : appIds) {
            ApplicationEntity application = applicationDao.findOne(appId);
            applications.add(application);
        }
        return applications;
    }

}
