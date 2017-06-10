/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.InstitutionDao;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 14:25
 */
@Repository
public class InstitutionDaoImpl extends BaseDaoImpl<InstitutionEntity, Long>
        implements InstitutionDao {

    /**
     * 根据登入名查询组织机构列表
     *
     * @param loginName
     * @return
     */
    @Override
    public List<InstitutionEntity> findInstByLoginName(String loginName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_institutions i where i.id in \n")
                .append("( select ui.institution_id from usms_user_institution ui ")
                .append(" where ui.user_id in ")
                .append(" (select u.id   from usms_users u ")
                .append("  where u.login_name = ?))");
        String sql = sb.toString();
        return super.queryBySql(InstitutionEntity.class, sql, new Object[]{loginName});
    }

    /**
     * 根据编码查询组织机构信息
     *
     * @param name
     * @return
     */
    @Override
    public InstitutionEntity findByName(String name) {
        String sql = "select * from usms_institutions where name = ?";
        List<InstitutionEntity> result = super.queryBySql(InstitutionEntity.class, sql, new Object[]{name});
        return JpaUtil.getSingleResult(result);
    }

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByInstName(String instName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in(  ")
                .append(" select  ui.user_id from usms_user_institution ui ")
                .append(" where ui.institution_id in( ")
                .append(" select i.id from usms_institutions i where i.name =?)) ");
        String sql = sb.toString();
        return super.queryBySql(UserEntity.class, sql, new Object[]{instName});
    }

    @Override
    public List<UserEntity> getUsersByInstNames(String[] instNames) {
        String queryParams = JpaUtil.getQuestionMarks(instNames);
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in ( \n")
                .append("select distinct ui.user_id from usms_user_institution ui where ui.institution_id in (\n")
                .append("select i.id from usms_institutions i where i.name in (")
                .append(queryParams).append(") and i.enabled = 1 ))");
        String sql = sb.toString();
        return super.queryBySql(UserEntity.class, sql, instNames);
    }

    @Override
    public List<InstitutionEntity> getInstitutionsByType(Long type) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_institutions i ")
                .append(" where i.type = ? and enabled = 1");
        String sql = sb.toString();
        return super.queryBySql(InstitutionEntity.class, sql, new Object[]{type});
    }

    @Override
    public boolean canBeDeleted(Long id) {
        String sql = "select i.id from usms_institutions i where i.parent_id = ?";
        List<Object> children = super.queryObject(sql, new Object[]{id});
        if (children != null && children.size() > 0) return false;
        sql = "select ui.user_id from usms_user_institution ui where ui.institution_id = ?";
        List<Object> users = super.queryObject(sql, new Object[]{id});
        if (users != null && users.size() > 0) return false;
        return true;
    }

}

