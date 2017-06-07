/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.model.UserModel;
import net.evecom.common.usms.uma.dao.UserDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述 UserDao实现
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:46
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserEntity, Long> implements UserDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据分页查询用户Model
     *
     * @param page 当前页码
     * @param size 页面数据量
     * @return
     */
    @Override
    public Page<UserModel> findModelsByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        sb.append("select u.id, u.login_name, u.name, s.mobile, u.enabled\n")
                .append(" from usms_users u  " )
                .append(" left join usms_staffs s\n")
                .append(" on u.staff_id = s.id where 1=1 "+sqlFilter.getWhereSql());
        String sql = sb.toString();
        Page<Map<String, Object>> pageBean = queryMapByPage(sql, sqlFilter.getParams().toArray(), page, size);
        List<UserModel> list = new ArrayList<>();
        for (Map<String, Object> var : pageBean.getContent()) {
            UserModel userModel = new UserModel();
            Long id = ((BigDecimal) var.get("ID")).longValue();
            List<RoleEntity> roles = findRolesById(id);
            // 构造角色字符串
            List<String> roleNames = new ArrayList<>();
            for (RoleEntity role : roles) {
                roleNames.add(role.getLabel());
            }
            userModel.setId(id);
            userModel.setLoginName((String) var.get("LOGIN_NAME"));
            userModel.setName((String) var.get("NAME"));
            userModel.setMobile((String) var.get("MOBILE"));
            userModel.setEnabled(((BigDecimal) var.get("ENABLED")).longValue());
            userModel.setRoleNames(roleNames);
            list.add(userModel);
        }
        return new PageImpl<>(list, new PageRequest(page, size), pageBean.getTotalElements());
    }

    @Override
    public UserEntity findByLoginName(String loginName) {
        String sql = "select * from usms_users u where u.login_name = ?";
        List<UserEntity> result = super.queryBySql(sql, new Object[]{loginName});
        return JpaUtil.getSingleResult(result);
    }

    @Override
    public List<UserEntity> findByLoginNames(String[] loginNames) {
        StringBuffer queryParams = new StringBuffer();
        if(loginNames != null && loginNames.length > 0) {
            int i = 0;
            for (String ignored : loginNames) {
                if (i == 0) queryParams.append("?");
                else queryParams.append(",?");
                i++;
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.login_name in (")
                .append(queryParams)
                .append(")");
        String sql = sb.toString();
        return super.queryBySql(sql, loginNames);
    }


    @Override
    public List<RoleEntity> findRolesById(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_roles r where r.id in (\n")
                .append("select ur.role_id from usms_user_role ur where ur.user_id = ?\n")
                .append(") and r.enabled = 1");
        String sql = sb.toString();
        return super.queryBySql(RoleEntity.class, sql, new Object[]{id});
    }

    @Override
    public List<OperationEntity> findOperationsById(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations o\n")
                .append(" where o.id in\n")
                .append(" (select po.oper_id\n")
                .append(" from usms_privilege_operation po\n")
                .append(" where po.priv_id in\n")
                .append(" (select p.id\n")
                .append(" from usms_privileges p\n")
                .append(" where p.id in (select pr.priv_id\n")
                .append(" from usms_privilege_role pr, usms_roles r\n")
                .append(" where pr.role_id in\n")
                .append(" (select ur.role_id\n")
                .append(" from usms_user_role ur\n")
                .append(" where ur.user_id = ?)\n")
                .append(" and pr.role_id = r.id\n")
                .append(" and r.enabled = 1)\n")
                .append(" and p.enabled = 1))\n")
                .append(" and o.enabled = 1");
        String sql = sb.toString();
        return super.queryBySql(OperationEntity.class, sql, new Object[]{id});
    }

    @Override
    public List<ApplicationEntity> findApplicationsById(Long id) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_applications a where a.id in (\n")
                .append("select distinct o.application_id from usms_operations o\n")
                .append(" where o.id in (select po.oper_id\n")
                .append(" from usms_privilege_operation po\n")
                .append(" where po.priv_id in \n")
                .append(" (select p.id from usms_privileges p\n")
                .append(" where p.id in (select pr.priv_id\n")
                .append(" from usms_privilege_role pr, usms_roles r\n")
                .append(" where pr.role_id in \n")
                .append(" (select ur.role_id from usms_user_role ur where ur.user_id = ?)\n")
                .append(" and pr.role_id = r.id and r.enabled = 1)\n")
                .append(" and p.enabled = 1)) and o.enabled = 1 and o.application_id is not null)");
        String sql = sb.toString();
        return super.queryBySql(ApplicationEntity.class, sql, new Object[]{id});
    }

    @Override
    public List<GridEntity> findGridsByLoginName(String loginName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from gsmp_loc_grids g\n")
                .append(" where g.code in\n")
                .append(" (select ug.grid_code\n")
                .append(" from usms_user_grid ug\n")
                .append(" where ug.user_id =\n")
                .append(" (select u.id from usms_users u where u.login_name = ?))\n");
        String sql = sb.toString();
        return super.queryBySql(GridEntity.class, sql, new Object[]{loginName});
    }

    @Override
    public void updateInstitutions(Long userId, String[] institutionIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from usms_user_institution ui where user_id=:userId");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
        if (institutionIds != null) {
            for (String id : institutionIds) {
                Long institutionId = Long.valueOf(id);
                sql = "insert into usms_user_institution values(:userId, :institutionId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("userId", userId);
                query.setParameter("institutionId", institutionId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public List<InstitutionEntity> findInstByUserId(Long userId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_institutions i where i.id in ")
                .append("(select ui.institution_id from usms_user_institution ui ")
                .append(" where ui.user_id = ?)");
        String sql = sb.toString();
        return super.queryBySql(InstitutionEntity.class, sql, new Object[]{userId});
    }

    @Override
    public void createUserInstitution(Long userId, Long institutionId) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO USMS_USER_INSTITUTION VALUES(:userId,:institutionId)");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql);
        query.setParameter("userId",userId);
        query.setParameter("institutionId",institutionId);
        query.executeUpdate();
    }
}
