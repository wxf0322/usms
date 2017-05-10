/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.InstitutionDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 14:25
 */
@Repository
public class InstitutionDaoImpl implements InstitutionDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据登入名查询组织机构列表
     *
     * @param loginName
     * @return
     */
    @Override
    public List<InstitutionEntity> findInstByLoginName(String loginName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_institutions i\n")
                .append("where i.id in (select si.institution_id\n")
                .append(" from usms_staff_institution si\n")
                .append(" where si.staff_id = (\n")
                .append("  select s.id\n")
                .append("  from usms_staffs s\n")
                .append("  where s.id = (\n")
                .append("   select u.staff_id\n")
                .append("   from usms_users u\n")
                .append("   where u.login_name = :loginName\n")
                .append("    and u.enabled = 1\n")
                .append("   )\n")
                .append("  )) and i.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, InstitutionEntity.class);
        query.setParameter("loginName", loginName);
        return query.getResultList();
    }

    @Override
    public InstitutionEntity getByName(String name) {
        TypedQuery<InstitutionEntity> query =
                manager.createNamedQuery(InstitutionEntity.QUERY_BY_NAME, InstitutionEntity.class);
        query.setParameter(InstitutionEntity.PARAM_NAME, name);
        return JpaUtil.getSingleResult(query.getResultList());
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
        sb.append("select * from usms_users u\n")
                .append(" where u.staff_id in\n")
                .append(" (select usi.staff_id from usms_staff_institution usi where usi.institution_id in\n")
                .append(" (select i.id from usms_institutions i where i.name = :name))\n")
                .append(" and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", instName);
        return query.getResultList();
    }

}
