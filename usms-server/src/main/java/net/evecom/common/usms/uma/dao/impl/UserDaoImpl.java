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
import net.evecom.common.usms.uma.dao.RoleJpa;
import net.evecom.common.usms.uma.dao.UserJpa;
import net.evecom.common.usms.vo.UserVO;
import net.evecom.common.usms.uma.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
     * 注入UserJpa
     */
    @Autowired
    private UserJpa userJpa;

    /**
     * 注入RoleJpa
     */
    @Autowired
    private RoleJpa roleJpa;

    /**
     * 根据分页查询用户Model
     *
     * @param page          当前页码
     * @param size          页面数据量
     * @param institutionId
     * @param sqlFilter
     * @return
     */
    @Override
    public Page<UserVO> listUsersByPage(int page, int size, Long institutionId, SqlFilter sqlFilter) {
        String instCondition;
        List params = new ArrayList();
        if (institutionId == null) {
            instCondition = "ui.institution_id is null";
        } else {
            instCondition = "ui.institution_id = ?";
            params.add(institutionId);
        }
        params.addAll(sqlFilter.getParams());
        StringBuffer sb = new StringBuffer();
        sb.append("select u.id, u.login_name, u.name, u.enabled, s.mobile from (\n")
                .append("select distinct u.id, u.login_name, u.name, u.staff_id, u.enabled, ui.institution_id \n")
                .append("from usms_users u \n")
                .append("left join usms_user_institution ui on u.id = ui.user_id\n")
                .append("where ").append(instCondition).append(") u \n")
                .append("left join usms_staffs s\n")
                .append("on u.staff_id = s.id ")
                .append(sqlFilter.getWhereSql());
        String sql = sb.toString();
        Page<Map<String, Object>> pageBean = queryForMap(sql, params.toArray(), page, size);
        List<UserVO> results = new ArrayList<>();
        for (Map<String, Object> var : pageBean.getContent()) {
            UserVO userVO = new UserVO();
            userVO.setId(((BigDecimal) var.get("ID")).longValue());
            userVO.setLoginName((String) var.get("LOGIN_NAME"));
            userVO.setName((String) var.get("NAME"));
            userVO.setMobile((String) var.get("MOBILE"));
            userVO.setEnabled(((BigDecimal) var.get("ENABLED")).longValue());
            results.add(userVO);
        }
        return new PageImpl<>(results, new PageRequest(page, size), pageBean.getTotalElements());
    }

    @Override
    public UserEntity getUserByLoginName(String loginName) {
        List<UserEntity> result = userJpa.findByLoginName(loginName);
        return JpaUtil.getSingleResult(result);
    }

    @Override
    public List<UserEntity> listUsersByLoginNames(String[] loginNames) {
        String queryParams = JpaUtil.getQuestionMarks(loginNames);
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.login_name in (")
                .append(queryParams).append(")");
        String sql = sb.toString();
        return super.queryForClass(sql, loginNames);
    }

    @Override
    public List<RoleEntity> listRolesByUserId(Long userId) {
        List<RoleEntity> result = super.namedQueryForClass("User.listRolesByUserId",
                new Object[]{userId});
        return result;
    }

    @Override
    public List<OperationEntity> listOpersByUserId(Long userId) {
        List<OperationEntity> result = super.namedQueryForClass("User.listOpersByUserId",
                new Object[]{userId});
        return result;
    }

    @Override
    public List<ApplicationEntity> listAppsByUserId(Long userId) {
        List<ApplicationEntity> result = super.namedQueryForClass("User.listAppsByUserId",
                new Object[]{userId});
        return result;
    }

    @Override
    public List<GridEntity> listGridsByLoginName(String loginName) {
        List<GridEntity> result = super.namedQueryForClass("User.listGridsByLoginName",
                new Object[]{loginName});
        return result;

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
    public List<InstitutionEntity> listInstsByUserId(Long userId) {
        List<InstitutionEntity> result = super.namedQueryForClass("User.listInstsByUserId",
                new Object[]{userId});
        return result;
    }

    @Override
    public List<RoleEntity> listTargetRoles(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        } else {
            List<RoleEntity> result = super.namedQueryForClass("User.listTargetRoles",
                    new Object[]{userId});
            return result;
        }
    }

    @Override
    public List<RoleEntity> listSourceRoles(Long userId) {
        if (userId != null) {
            //编辑
            List<RoleEntity> result = super.namedQueryForClass("User.listSourceRoles",
                    new Object[]{userId});
            return result;
        } else {
            //新增
            List<RoleEntity> result = roleJpa.findByEnabled(1L);
            return result;
        }
    }

    @Override
    public void updateRoles(Long userId, String[] roleIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from usms_user_role where user_id =:userId");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
        if (roleIds != null) {
            for (String id : roleIds) {
                Long roleId = Long.valueOf(id);
                sql = "insert into usms_user_role values(:roleId,:userId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("userId", userId);
                query.executeUpdate();
            }
        }
    }

}
