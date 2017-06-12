/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.entity.OperationEntity;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.OperationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:20
 */
@Repository
public class OperationDaoImpl extends BaseDaoImpl<OperationEntity, Long>
        implements OperationDao {
    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据app名称获取操作列表
     *
     * @param appName
     * @return List<OperationEntity>
     */
    @Override
    public List<OperationEntity> findOperationsByAppName(String appName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations o\n")
                .append("where o.application_id in (")
                .append("select a.id from usms_applications a ")
                .append("where a.name =:application) and o.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, OperationEntity.class);
        query.setParameter("application", appName);
        return query.getResultList();
    }

    /**
     * 根据操作编码查询用户列表
     *
     * @param operName
     * @return
     */
    @Override
    public List<UserEntity> findUsersByOperName(String operName) {
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

    /**
     * 判断是否拥有该操作
     *
     * @param userID
     * @param operationName
     * @return boolean
     */
    @Override
    public boolean hasOperation(long userID, String operationName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations p where p.id in( ")
                .append("select po.oper_id from usms_privilege_operation po where po.priv_id in( ")
                .append("select pr.priv_id from usms_privilege_role pr where pr.role_id in( ")
                .append("select ur.role_id from usms_user_role ur where ur.user_id = :userid ))) ")
                .append("and p.enabled=1 and p.name= :operationName");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql);
        query.setParameter("userid", userID);
        query.setParameter("operationName", operationName);
        return query.getResultList().size() != 0;
    }

}
