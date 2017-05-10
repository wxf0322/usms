/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.RoleDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Pisces
 * @version 1.0
 * @created 2017-5-8 18:24
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<RoleEntity> findRolesByUserId(long userID) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from USMS_ROLES r where r.id in( ")
                .append("select ur.role_id from USMS_USER_ROLE ur ")
                .append("where ur.user_id =:userid) and r.enabled=1 ");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, RoleEntity.class);
        query.setParameter("userid", userID);
        return query.getResultList();
    }


    @Override
    public boolean hasRole(long userID,String RoleName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from USMS_ROLES r where r.id in( ")
                .append("select ur.role_id from USMS_USER_ROLE ur ")
                .append("where ur.user_id =:userid) and r.enabled=1 and r.name=:name ");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, RoleEntity.class);
        query.setParameter("userid", userID);
        query.setParameter("name", RoleName);
        return query.getResultList().size()!=0;
    }

    /**
     * 根据角色编码查询用户列表
     *
     * @param roleName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByRoleName(String roleName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in\n")
                .append(" (select r.id from usms_roles r where r.name = :name))\n")
                .append(" and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", roleName);
        return query.getResultList();
    }
}
