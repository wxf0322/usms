/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.UserModel;
import net.evecom.common.usms.uma.dao.RoleDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 18:24
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleEntity, Long> implements RoleDao {
    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据用户id获取角色列表
     *
     * @param userID
     * @return List<RoleEntity>
     */
    @Override
    public List<RoleEntity> findRolesByUserId(long userID) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_roles r where r.id in( ")
                .append("select ur.role_id from usms_user_role ur ")
                .append("where ur.user_id = ?) and r.enabled = 1 ");
        String sql = sb.toString();
        return super.queryBySql(sql, new Object[]{userID});
    }

    /**
     * 判断是否拥有该角色
     *
     * @param userID
     * @param roleName
     * @return boolean
     */
    @Override
    public boolean hasRole(long userID, String roleName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_roles r where r.id in( ")
                .append("select ur.role_id from usms_user_role ur ")
                .append("where ur.user_id = ?) and r.enabled=1 and r.name= ? ");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, RoleEntity.class);
        query.setParameter(1, userID);
        query.setParameter(2, roleName);
        return query.getResultList().size() != 0;
    }

    /**
     * 根据角色编码查询用户列表
     *
     * @param roleName
     * @return
     */
    @Override
    public List<UserEntity> findUsersByRoleName(String roleName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in \n")
                .append(" (select r.id from usms_roles r where r.name = ? and r.enabled = 1))\n")
                .append(" and u.enabled = 1");
        String sql = sb.toString();
        return super.queryBySql(UserEntity.class, sql, new Object[]{roleName});
    }

    /**
     * @param roleNames
     * @return
     */
    @Override
    public List<UserEntity> findUsersByRoleNames(String[] roleNames) {
        String queryParams = JpaUtil.getQuestionMarks(roleNames);
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in\n")
                .append(" (select r.id from usms_roles r where r.name in (")
                .append(queryParams).append(") and r.enabled = 1 ))\n")
                .append(" and u.enabled = 1");
        String sql = sb.toString();
        return super.queryBySql(UserEntity.class, sql, roleNames);
    }

    /**
     * 查找所有角色列表
     *
     * @return
     */
    @Override
    public Page<RoleEntity> findByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_roles r  "+sqlFilter.getWhereSql());
        String sql = sb.toString();
        return queryByPage(sql, sqlFilter.getParams().toArray(), page, size);
    }

    /**
     * 根据角色id查找用户列表
     */
    public List<UserEntity> findUsersByRoleId(Long roleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id = ? )\n");
        String sql = sb.toString();
        return super.queryBySql(UserEntity.class, sql, new Object[]{roleId});
    }

    /**
     * 查找角色Id对应的已选权限列表
     */
    @Override
    public List<PrivilegeEntity> getSelectedPrivileges(Long roleId) {
        StringBuffer sb = new StringBuffer();
        if(roleId==null){
            return new ArrayList<>();
        }else{
            sb.append("select * from usms_privileges where id in(")
                    .append("select priv_id from usms_privilege_role t ")
                    .append("where role_id=:roleId) and enabled=1 ");
            String sql = sb.toString();
            Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
            query.setParameter("roleId", roleId);
            return query.getResultList();
        }
    }

    /**
     * 查找角色Id对应的未选权限列表
     */
    @Override
    public List<PrivilegeEntity> getUnselectedPrivileges(Long roleId) {
        StringBuffer sb = new StringBuffer();
        if(roleId!=null){
            sb.append("select * from usms_privileges where id not in(")
                    .append("select priv_id from usms_privilege_role t ")
                    .append("where role_id=?) and enabled=1 ");
            String sql = sb.toString();
            Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
            query.setParameter(1, roleId);
            return query.getResultList();
        }else {
            sb.append("select * from usms_privileges where enabled=1");
            String sql = sb.toString();
            Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
            return query.getResultList();
        }
    }

    /**
     * 更新角色对应的权限列表
     *
     * @param roleId
     * @param privilegeIds
     */
    @Override
    public void updatePrivileges(Long roleId, String[] privilegeIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from usms_privilege_role where role_id =:roleId");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql);
        query.setParameter("roleId", roleId);
        query.executeUpdate();
        if (privilegeIds != null) {
            for (String id : privilegeIds) {
                Long privilegeId = Long.valueOf(id);
                sql = "INSERT INTO usms_privilege_role VALUES(:privilegeId,:roleId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("privilegeId", privilegeId);
                query.executeUpdate();
            }
        }
    }

    /**
     * 根据角色ID查找已选用户列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Map<String, Object>> getSelectedUsers(Long roleId) {
        StringBuffer sb = new StringBuffer();
        if(roleId==null){
            return new ArrayList<>();
        }else {
            sb.append("select id,login_name,name from usms_users where id in(\n")
                    .append("  select user_id from usms_user_role t\n")
                    .append("where role_id=?) and enabled=1");
            String sql = sb.toString();
            return super.queryMap(sql, new Object[]{roleId});
        }
    }

    @Override
    public List<Map<String, Object>> getUnselectedUsers(Long roleId) {
        StringBuffer sb = new StringBuffer();
        if(roleId==null){
            sb.append("select id,login_name, name from usms_users where enabled=1");
            String sql = sb.toString();
            return super.queryMap(sql,new Object[]{});
        }
        sb.append("select id,login_name, name from usms_users where id not in(\n")
                .append(" select user_id from usms_user_role t\n")
                .append("where role_id=?) and enabled=1");
        String sql = sb.toString();
        return super.queryMap(sql, new Object[]{roleId});
    }

    @Override
    public void updateUsers(Long roleId, String[] userIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from usms_user_role where role_id =:roleId");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql);
        query.setParameter("roleId", roleId);
        query.executeUpdate();
        if (userIds != null) {
            for (String id : userIds) {
                Long userId = Long.valueOf(id);
                sql = "INSERT INTO usms_user_role VALUES(:roleId,:userId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("userId", userId);
                query.executeUpdate();
            }
        }
    }

}
