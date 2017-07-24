/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.MapUtil;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.uma.dao.custom.PrivilegeDaoCustom;
import net.evecom.common.usms.vo.OperationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午6:10
 */
@Repository
public class PrivilegeDaoImpl extends BaseDaoImpl<PrivilegeEntity> implements PrivilegeDaoCustom {

    /**
     * 日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(PrivilegeDaoImpl.class);

    /**
     * 查找权限列表
     *
     * @param page
     * @param size
     * @param sqlFilter
     * @return
     */
    @Override
    public Page<PrivilegeEntity> listPrivsByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_privileges p ")
                .append(sqlFilter.getWhereSql())
                .append(" order by p.id desc");
        String sql = sb.toString();
        return super.queryForClass(sql, sqlFilter.getParams().toArray(), page, size);
    }

    @Override
    public void updateOperations(Long privilegeId, String[] operationIds) {
        String sql ="delete from usms_privilege_operation where priv_id =:privilegeId";
        Query query = manager.createNativeQuery(sql);
        query.setParameter("privilegeId", privilegeId);
        query.executeUpdate();
        if (operationIds != null) {
            for (String id : operationIds) {
                Long operationId = Long.valueOf(id);
                sql = "insert into usms_privilege_operation values(:privilegeId,:operationId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("privilegeId", privilegeId);
                query.setParameter("operationId", operationId);
                query.executeUpdate();
            }
        }
    }

    @Override
    public void updateRoles(Long privilegeId, String[] roleIds) {
        String sql = "delete from usms_privilege_role where priv_id =:privilegeId";
        Query query = manager.createNativeQuery(sql);
        query.setParameter("privilegeId", privilegeId);
        query.executeUpdate();
        if (roleIds != null) {
            for (String id : roleIds) {
                Long roleId = Long.valueOf(id);
                sql = "insert into usms_privilege_role values(:privilegeId,:roleId)";
                query = manager.createNativeQuery(sql);
                query.setParameter("roleId", roleId);
                query.setParameter("privilegeId", privilegeId);
                query.executeUpdate();
            }
        }
    }

}
