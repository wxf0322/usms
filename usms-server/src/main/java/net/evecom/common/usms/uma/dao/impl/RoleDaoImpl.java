/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.RoleDaoCustom;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午6:32
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleEntity> implements RoleDaoCustom {

    @Override
    public void updatePrivileges(Long roleId, String[] privilegeIds) {
        String sql = "delete from usms_privilege_role where role_id =:roleId";
        Query query = manager.createNativeQuery(sql);
        query.setParameter("roleId", roleId);
        query.executeUpdate();
        if (privilegeIds != null) {
            for (String id : privilegeIds) {
                Long privilegeId = Long.valueOf(id);
                sql = "insert into usms_privilege_role values(:privilegeId,:roleId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("privilegeId", privilegeId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public List<UserEntity> listUsersByRoleNames(String[] roleNames) {
        String queryParams = JpaUtil.getQuestionMarks(roleNames);
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in")
                .append(" (select ur.user_id from usms_user_role ur")
                .append(" where ur.role_id in")
                .append(" (select r.id from usms_roles r where r.name in (")
                .append(queryParams).append(") and r.enabled = 1 ))")
                .append(" and u.enabled = 1");
        String sql = sb.toString();
        return super.queryForClass(UserEntity.class, sql, roleNames);
    }

    @Override
    public Page<RoleEntity> listRolesByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_roles r ").append(sqlFilter.getWhereSql());
        String sql = sb.toString();
        return queryForClass(sql, sqlFilter.getParams().toArray(), page, size);
    }

    @Override
    public List<Map<String, Object>> listTargetUsers(Long roleId, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        if (roleId == null) {
            return new ArrayList<>();
        } else {
            sb.append("select id, login_name, name from usms_users where id in(")
                    .append(" select user_id from usms_user_role t")
                    .append(" where role_id=?) and enabled=1");
            String sql = sb.toString();
            return super.queryForMap(sql, new Object[]{roleId});
        }
    }

    @Override
    public List<Map<String, Object>> listSourceUsers(Long roleId, Long institutionId, String key) {
        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        String searchWord = "%" + key + "%";
        String sql;
        if (roleId == null) {
            // 新增
            if (institutionId == null) {
                sb.append("select * from usms_users u where u.name like ?");
            } else {
                sb.append("select * from (select distinct u.id, u.login_name, u.name ")
                        .append(" from usms_users u where u.id in ")
                        .append(" (select ui.user_id from usms_user_institution ui where ui.institution_id = ?)) u ")
                        .append(" where u.name like ?");
                params.add(institutionId);
            }
            sql = sb.toString();
        } else {
            // 修改
            if (institutionId == null) {
                sb.append("select * from usms_users u where u.id not in ( ")
                        .append("select user_id from usms_user_role r where r.role_id = ? ")
                        .append(") and u.name like ?");
                params.add(roleId);
            } else {
                sb.append("select * from usms_users u where u.id in ( ")
                        .append("select ui.user_id from usms_user_institution ui where ui.user_id not in ")
                        .append("(select user_id from usms_user_role r where r.role_id = ?) ")
                        .append("and ui.institution_id = ? ) and u.name like ?");
                params.add(roleId);
                params.add(institutionId);
            }
            sql = sb.toString();
        }
        params.add(searchWord);
        return super.queryForMap(sql, params.toArray());
    }

    @Override
    public void updateUsers(Long roleId, String[] userIds) {
        String sql = "delete from usms_user_role where role_id =:roleId";
        Query query = manager.createNativeQuery(sql);
        query.setParameter("roleId", roleId);
        query.executeUpdate();
        if (userIds != null) {
            for (String id : userIds) {
                Long userId = Long.valueOf(id);
                sql = "insert into usms_user_role values(:roleId,:userId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("userId", userId);
                query.executeUpdate();
            }
        }
    }

}
