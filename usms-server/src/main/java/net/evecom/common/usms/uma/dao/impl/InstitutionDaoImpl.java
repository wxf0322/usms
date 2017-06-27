/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.ApplicationJpa;
import net.evecom.common.usms.uma.dao.InstitutionDao;
import net.evecom.common.usms.uma.dao.InstitutionJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
     * @see ApplicationJpa
     */
    @Autowired
    private InstitutionJpa institutionJpa;


    /**
     * 根据登入名查询组织机构列表
     *
     * @param loginName
     * @return
     */
    @Override
    public List<InstitutionEntity> listInstsByLoginName(String loginName) {
        List<InstitutionEntity> result = super.namedQueryForClass("Institution.listInstsByLoginName",
                new Object[]{loginName});
        return result;
    }

    /**
     * 根据编码查询组织机构信息
     *
     * @param instName
     * @return
     */
    @Override
    public InstitutionEntity getInstByInstName(String instName) {
        List<InstitutionEntity> result = institutionJpa.findByName(instName);
        return JpaUtil.getSingleResult(result);
    }

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByInstName(String instName) {
        List<UserEntity> result = super.namedQueryForClass("Institution.listUsersByInstName",
                new Object[]{instName});
        return result;
    }

    @Override
    public List<UserEntity> listUsersByInstNames(String[] instNames) {
        String queryParams = JpaUtil.getQuestionMarks(instNames);
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in ( \n")
                .append("select distinct ui.user_id from usms_user_institution ui where ui.institution_id in (\n")
                .append("select i.id from usms_institutions i where i.name in (")
                .append(queryParams).append(") and i.enabled = 1 ))");
        String sql = sb.toString();
        return super.queryForClass(UserEntity.class, sql, instNames);
    }

    @Override
    public List<InstitutionEntity> listInstsByType(Long type) {
        List<InstitutionEntity> result = institutionJpa.findByTypeAndEnabled(type,1L);
        return result;
    }

    @Override
    public boolean canBeDeleted(Long id) {
        String sql = "select i.id from usms_institutions i where i.parent_id = ?";
        List<Object> children = super.queryForObject(sql, new Object[]{id});
        if (children != null && children.size() > 0) return false;
        sql = "select ui.user_id from usms_user_institution ui where ui.institution_id = ?";
        List<Object> users = super.queryForObject(sql, new Object[]{id});
        if (users != null && users.size() > 0)
            return false;
        else
            return true;
    }

}

