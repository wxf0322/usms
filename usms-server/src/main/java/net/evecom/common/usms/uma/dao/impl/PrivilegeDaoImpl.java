/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:26
 */
@Repository
public class PrivilegeDaoImpl implements PrivilegeDao {
    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据App名称获取权限列表
     * @return List<PrivilegeEntity>
     * @param application
     */
    @Override
    public List<PrivilegeEntity> getPrivilegesByAppName(String application) {
        StringBuffer sb = new StringBuffer();
        sb.append("select *  from usms_privileges p ")
                .append("where p.id in (select po.priv_id from usms_privilege_operation po ")
                .append("where po.oper_id in (select o.id ")
                .append("from usms_operations o ")
                .append("where o.application_id in ")
                .append(" (select a.id  from usms_applications a ")
                .append("where a.name=:application)")
                .append("and o.enabled = 1))");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
        query.setParameter("application", application);
        return query.getResultList();
    }

    /**
     * 根据用户名查找权限列表
     * @return List<PrivilegeEntity>
     * @param userID
     */
    @Override
    public List<PrivilegeEntity> getPrivilegesByUserId(long userID) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from USMS_PRIVILEGES p ")
                .append("where p.id in( ")
                .append("select pr.priv_id from USMS_PRIVILEGE_ROLE pr ")
                .append("where pr.role_id in( ")
                .append("select ur.role_id from USMS_USER_ROLE ur ")
                .append("where ur.user_id =:userid) ")
                .append(") and p.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
        query.setParameter("userid", userID);
        return query.getResultList();
    }

    /**
     * 判断是否拥有该权限
     * @return boolean
     * @param userID, privilegeName
     */
    @Override
    public boolean hasPrivilege(long userID, String privilegeName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from USMS_PRIVILEGES p ")
                .append("where p.id in( ")
                .append("select pr.priv_id from USMS_PRIVILEGE_ROLE pr ")
                .append("where pr.role_id in( ")
                .append("select ur.role_id from USMS_USER_ROLE ur ")
                .append("where ur.user_id =:userid) ")
                .append(") and p.enabled = 1 and p.name=:name");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
        query.setParameter("userid", userID);
        query.setParameter("name", privilegeName);
        return query.getResultList().size()!=0;
    }


    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByPrivName(String privName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u\n")
                .append(" where u.id in (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in (select r.id from usms_roles r\n")
                .append(" where r.id in (select pr.role_id from usms_privilege_role pr\n")
                .append(" where pr.priv_id in (select p.id from usms_privileges p\n")
                .append(" where p.name = :name)) and r.enabled = 1)) and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", privName);
        return query.getResultList();
    }
}
