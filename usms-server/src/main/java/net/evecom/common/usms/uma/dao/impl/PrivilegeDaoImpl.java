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
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.RoleJpa;
import net.evecom.common.usms.vo.OperationVO;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限管理Dao实现类
 *
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:26
 */
@Repository
public class PrivilegeDaoImpl extends BaseDaoImpl<PrivilegeEntity, Long>
        implements PrivilegeDao {

    /**
     * 日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(PrivilegeDaoImpl.class);

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 注入PrivilegeJpa
     */
    @Autowired
    private RoleJpa roleJpa;

    /**
     * 根据应用编码获取权限列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<PrivilegeEntity> listPrivsByAppName(String appName) {
        List<PrivilegeEntity> result = super.namedQueryForClass("Privilege.listPrivsByAppName",
                new Object[]{appName});
        return result;
    }

    /**
     * 根据用户名查找权限列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<PrivilegeEntity> listPrivsByUserId(long userId) {
        List<PrivilegeEntity> result = super.namedQueryForClass("Privilege.listPrivsByUserId",
                new Object[]{userId});
        return result;
    }

    /**
     * 判断是否拥有该权限
     *
     * @param userId
     * @param privName
     * @return boolean
     */
    @Override
    public boolean hasPrivilege(long userId, String privName) {
        List<PrivilegeEntity> result = super.namedQueryForClass("Privilege.hasPrivilege",
                new Object[]{userId,privName});
        return result.size() != 0;
    }

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByPrivName(String privName) {
        List<UserEntity> result = super.namedQueryForClass("Privilege.listUsersByPrivName",
                new Object[]{privName});
        return  result;
    }

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
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_privileges p "+sqlFilter.getWhereSql());
        String sql = sb.toString();
        return super.queryForClass(sql, sqlFilter.getParams().toArray(), page, size);
    }

    @Override
    public void updateOperations(Long privilegeId, String[] operationIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from usms_privilege_operation where priv_id =:privilegeId");
        String sql = sb.toString();
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
    public List<OperationVO> listOpersByPrivId(Long privilegeId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations where id in(\n")
                .append(" select oper_id from usms_privilege_operation t\n")
                .append("where priv_id=?) and enabled=1");
        String sql = sb.toString();
        List<Map<String, Object>> rows = super.queryForMap(sql, new Object[]{privilegeId});
        List<OperationVO> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            try {
                Map<String, Object> camelMap = MapUtil.toCamelCaseMap(row);
                OperationVO operation = MapUtil.toObject(OperationVO.class ,camelMap);
                result.add(operation);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public List<RoleEntity> listTargetRoles(Long privilegeId) {
        StringBuffer sb = new StringBuffer();
        if (privilegeId == null) {
            //新增时候
            return new ArrayList<>();
        } else {
            //编辑时候
            List<RoleEntity> result = super.namedQueryForClass("Privilege.listTargetRoles",
                    new Object[]{privilegeId});
            return result;
        }
    }

    @Override
    public List<RoleEntity> listSourceRoles(Long privilegeId) {
        StringBuffer sb = new StringBuffer();
        if (privilegeId != null) {
            // 编辑
            List<RoleEntity> result = super.namedQueryForClass("Privilege.listSourceRoles",
                    new Object[]{privilegeId});
            return result;
        } else {
            // 新增
            List<RoleEntity> result = roleJpa.findByEnabled(1L);
            return result;
        }
    }

    @Override
    public void updateRoles(Long privilegeId, String[] roleIds) {
        StringBuffer sb = new StringBuffer();
        sb.append("delete from usms_privilege_role where priv_id =:privilegeId");
        String sql = sb.toString();
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
