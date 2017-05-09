/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.entity.OperationEntity;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import net.evecom.common.usms.uma.dao.OperationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 14:20
 */
@Repository
public class OperationDaoImpl implements OperationDao{
    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<OperationEntity> getOperationsByAppName(String application) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM USMS_OPERATIONS o\n")
                .append("WHERE o.APPLICATION_ID in (")
                .append("select a.id from USMS_APPLICATIONS a ")
                .append("where a.name =:application) and o.enabled = 1 ");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, OperationEntity.class);
        query.setParameter("application", application);
        return query.getResultList();
    }

    /**
     * 根据操作编码查询用户列表
     *
     * @param operName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByOperName(String operName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in (select r.id from usms_roles r\n")
                .append(" where r.id in (select pr.role_id from usms_privilege_role pr\n")
                .append(" where pr.priv_id in (select p.id from usms_privileges p\n")
                .append(" where p.id in (select po.priv_id from usms_privilege_operation po\n")
                .append(" where po.oper_id in (select o.id from usms_operations o\n")
                .append(" where o.name = :name)) and p.enabled = 1))\n")
                .append(" and r.enabled = 1)) and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", operName);
        return query.getResultList();
    }

}
