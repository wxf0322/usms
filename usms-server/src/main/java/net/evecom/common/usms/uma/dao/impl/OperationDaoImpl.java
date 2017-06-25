/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.entity.OperationEntity;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.OperationDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作相关实现类
 *
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:20
 */
@Repository
public class OperationDaoImpl extends BaseDaoImpl<OperationEntity, Long>
        implements OperationDao {

    /**
     * 根据app名称获取操作列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<OperationEntity> listOpersByAppName(String appName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations o\n")
                .append("where o.application_id in (")
                .append("select a.id from usms_applications a ")
                .append("where a.name = ?) and o.enabled = 1");
        String sql = sb.toString();
        return super.queryForClass(sql, new Object[]{appName});
    }

    /**
     * 根据操作编码查询用户列表
     *
     * @param operName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByOperName(String operName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in (select r.id from usms_roles r\n")
                .append(" where r.id in (select pr.role_id from usms_privilege_role pr\n")
                .append(" where pr.priv_id in (select p.id from usms_privileges p\n")
                .append(" where p.id in (select po.priv_id from usms_privilege_operation po\n")
                .append(" where po.oper_id in (select o.id from usms_operations o\n")
                .append(" where o.name = :name)) and p.enabled = 1))\n")
                .append(" and r.enabled = 1)) and u.enabled = 1");
        String sql = sb.toString();
        return super.queryForClass(UserEntity.class, sql, new Object[]{operName});
    }

    /**
     * 判断是否拥有该操作
     *
     * @param userId
     * @param operName
     * @return boolean
     */
    @Override
    public boolean hasOperation(long userId, String operName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations p where p.id in( ")
                .append("select po.oper_id from usms_privilege_operation po where po.priv_id in( ")
                .append("select pr.priv_id from usms_privilege_role pr where pr.role_id in( ")
                .append("select ur.role_id from usms_user_role ur where ur.user_id = ? ))) ")
                .append("and p.enabled=1 and p.name= ?");
        String sql = sb.toString();
        List results = super.queryForClass(sql, new Object[]{userId, operName});
        return results.size() != 0;
    }

}
