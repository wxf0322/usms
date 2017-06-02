/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.ApplicationDao;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
public class ApplicationDaoImpl extends BaseDaoImpl<ApplicationEntity, Long>
        implements ApplicationDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<ApplicationEntity> findByPage(int page, int size) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_applications");
        String sql = sb.toString();
        return super.queryByPage(sql, null, page, size);
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

    /**
     * 根据应用编码查询用户列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByApplicationName(String appName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u\n")
                .append(" where u.id in (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in (select r.id from usms_roles r\n")
                .append(" where r.id in (select pr.role_id from usms_privilege_role pr\n")
                .append(" where pr.priv_id in (select p.id from usms_privileges p\n")
                .append(" where p.id in (select po.oper_id from usms_privilege_operation po\n")
                .append(" where po.oper_id in (select o.id from usms_operations o\n")
                .append(" where o.application_id in (select a.id from usms_applications a\n")
                .append(" where a.name = :name) and o.enabled = 1))\n")
                .append(" and p.enabled = 1)) and r.enabled = 1)) and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", appName);
        return query.getResultList();
    }

}
