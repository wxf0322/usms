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
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.OperationModel;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * 根据App名称获取权限列表
     *
     * @param application
     * @return List<PrivilegeEntity>
     */
    @Override
    public List<PrivilegeEntity> findPrivilegesByAppName(String application) {
        StringBuffer sb = new StringBuffer();
        sb.append("select *  from usms_privileges p ")
                .append("where p.id in (select po.priv_id from usms_privilege_operation po ")
                .append("where po.oper_id in (select o.id ")
                .append("from usms_operations o ")
                .append("where o.application_id in ")
                .append(" (select a.id  from usms_applications a ")
                .append("where a.name=:application)")
                .append("and o.enabled = 1))");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
        query.setParameter("application", application);
        return query.getResultList();
    }

    /**
     * 根据用户名查找权限列表
     *
     * @param userID
     * @return List<PrivilegeEntity>
     */
    @Override
    public List<PrivilegeEntity> findPrivilegesByUserId(long userID) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_privileges p ")
                .append("where p.id in( ")
                .append("select pr.priv_id from usms_privilege_role pr ")
                .append("where pr.role_id in( ")
                .append("select ur.role_id from usms_user_role ur ")
                .append("where ur.user_id =:userid) ")
                .append(") and p.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
        query.setParameter("userid", userID);
        return query.getResultList();
    }

    /**
     * 判断是否拥有该权限
     *
     * @param userID
     * @param privilegeName
     * @return boolean
     */
    @Override
    public boolean hasPrivilege(long userID, String privilegeName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_privileges p ")
                .append("where p.id in( ")
                .append("select pr.priv_id from usms_privilege_role pr ")
                .append("where pr.role_id in( ")
                .append("select ur.role_id from usms_user_role ur ")
                .append("where ur.user_id =:userid) ")
                .append(") and p.enabled = 1 and p.name=:name");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, PrivilegeEntity.class);
        query.setParameter("userid", userID);
        query.setParameter("name", privilegeName);
        return query.getResultList().size() != 0;
    }


    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    @Override
    public List<UserEntity> findUsersByPrivName(String privName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u\n")
                .append(" where u.id in (select ur.user_id from usms_user_role ur\n")
                .append(" where ur.role_id in (select r.id from usms_roles r\n")
                .append(" where r.id in (select pr.role_id from usms_privilege_role pr\n")
                .append(" where pr.priv_id in (select p.id from usms_privileges p\n")
                .append(" where p.name = :name)) and r.enabled = 1)) and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", privName);
        return query.getResultList();
    }

    /**
     * 查找权限列表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<PrivilegeEntity> findByPage(int page, int size, SqlFilter sqlFilter) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_privileges p "+sqlFilter.getWhereSql());
        String sql = sb.toString();
        return super.queryByPage(sql, sqlFilter.getParams().toArray(), page, size);
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
    public List<OperationModel> findOperationsByPrivId(Long privilegeId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_operations where id in(\n")
                .append(" select oper_id from usms_privilege_operation t\n")
                .append("where priv_id=?) and enabled=1");
        String sql = sb.toString();
        List<Map<String, Object>> rows = super.queryMap(sql, new Object[]{privilegeId});
        List<OperationModel> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            try {
                Map<String, Object> camelMap = MapUtil.toCamelCaseMap(row);
                OperationModel operation = MapUtil.toObject(OperationModel.class ,camelMap);
                result.add(operation);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
