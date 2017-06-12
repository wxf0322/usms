/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import net.evecom.common.usms.core.dao.BaseDao;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * <P><B>描述: </B> 数据层基础类  </P>
 *
 * @param <T>  实体类
 * @param <ID> 主键ID
 * @author Wash Wang
 * @version 2.0
 * @created 2017/05/23 12:00:00
 */
@Repository("baseDao")
public abstract class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

    /**
     * 日志管理器
     */
    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    /**
     * @see EntityManager
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 泛型类
     */
    private Class<T> clazz;

    /**
     * 构造方法
     */
    protected BaseDaoImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    /**
     * 描述： 根据语句去获取可执行的query对象
     *
     * @param sqlString
     * @param values
     * @return
     */
    private Query getExecuteQuery(String sqlString, Object[] values) {
        Query query = this.getExecuteQuery(null, sqlString, values);
        return query;
    }

    /**
     * 描述： 根据语句去获取可执行的query对象
     *
     * @param sqlString
     * @param values
     * @return
     */
    private Query getExecuteQuery(Class entityClass, String sqlString, Object[] values) {
        Query query;
        if (entityClass == null) {
            query = manager.createNativeQuery(sqlString);
        } else {
            query = manager.createNativeQuery(sqlString, entityClass);
        }
        if (values != null && values.length > 0) {
            for (int i = 0, j = 1; i < values.length; i++, j++) {
                if (values[i] instanceof Date) {
                    query.setParameter(j, (Date) values[i], TemporalType.DATE);
                } else {
                    query.setParameter(j, values[i]);
                }
            }
        }
        return query;
    }


    /**
     * 查询数据总量
     *
     * @param sqlString
     * @param values
     * @return
     */
    @Override
    public long getCountBySql(String sqlString, Object[] values) {
        String allSql = "select count(*) from (" + sqlString + ")";
        Query query = this.getExecuteQuery(allSql, values);
        return Long.valueOf(query.getSingleResult().toString());
    }

    /**
     * 直接查询
     *
     * @param sqlString
     * @param values
     * @return
     */
    @Override
    public List<Object> queryObject(String sqlString, Object[] values) {
        Query query = this.getExecuteQuery(sqlString, values);
        return query.getResultList();
    }

    /**
     * 分页查询
     *
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Object> queryObjectByPage(String sqlString, Object[] values, int page, int pageSize) {
        Query query = this.getExecuteQuery(sqlString, values);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return new PageImpl<Object>(query.getResultList(),
                new PageRequest(page, pageSize),
                getCountBySql(sqlString, values));
    }

    /**
     * 返回查询到的ListMap
     *
     * @param sqlString
     * @param values
     * @return
     */
    @Override
    public List<Map<String, Object>> queryMap(String sqlString, Object[] values) {
        Query query = this.getExecuteQuery(sqlString, values);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    /**
     * 返回分页过的Map
     *
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryMapByPage(String sqlString, Object[] values, int page, int pageSize) {
        Query query = this.getExecuteQuery(sqlString, values);

        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return new PageImpl<Map<String, Object>>(query.getResultList(),
                new PageRequest(page, pageSize),
                getCountBySql(sqlString, values));
    }

    /**
     * 根据sql语句查询。
     *
     * @param entityClass
     * @param sqlString   the sql string
     * @param values      the values
     * @param <M>
     * @return
     */
    @Override
    public <M> List<M> queryBySql(Class<M> entityClass, String sqlString, Object[] values) {
        Query query = this.getExecuteQuery(entityClass, sqlString, values);
        return query.getResultList();
    }

    /**
     * 根据sql语句查询。
     *
     * @param sqlString the sql string
     * @param values    the values
     * @return
     */
    @Override
    public List<T> queryBySql(String sqlString, Object[] values) {
        return queryBySql(this.clazz, sqlString, values);
    }

    /**
     * 根据sql语句查询。也可以执行返回数据集的存储过程
     *
     * @param entityClass
     * @param sqlString   the sql string
     * @param values      the values
     * @param page        the start
     * @param pageSize    the page size
     * @param <M>
     * @return
     */
    @Override
    public <M> List<M> queryBySql(Class<M> entityClass, String sqlString, Object[] values, int page, int pageSize) {
        Query query = this.getExecuteQuery(entityClass, sqlString, values);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * 根据sql语句查询。也可以执行返回数据集的存储过程
     *
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<T> queryBySql(String sqlString, Object[] values, int page, int pageSize) {
        return queryBySql(this.clazz, sqlString, values, page, pageSize);
    }

    /**
     * 根据sql语句查询。也可以执行返回数据集的存储过程
     *
     * @param entityClass
     * @param sqlString
     * @param values
     * @param <M>
     * @return
     */
    @Override
    public <M> M queryUniqueBySql(Class<M> entityClass, String sqlString, Object[] values) {
        Query query = this.getExecuteQuery(entityClass, sqlString, values);
        query.setMaxResults(1);
        List<M> list = query.getResultList();
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据sql语句查询，并返回一条记录, 适用于VModel
     *
     * @param sqlString
     * @param values
     * @return
     */
    @Override
    public T queryUniqueBySql(String sqlString, Object[] values) {
        return this.queryUniqueBySql(this.clazz, sqlString, values);
    }

    /**
     * 事务执行sql语句，也可执行无返回结果的存储过程
     *
     * @param sqlString
     * @param values
     * @return 受影响的记录数
     * @throws Exception
     */
    @Override
    public int executeSql(String sqlString, Object[] values) throws Exception {
        Query query = this.getExecuteQuery(sqlString, values);
        return query.executeUpdate();
    }

    /**
     * 分页查询，返回分页对象
     *
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<T> queryByPage(String sqlString, Object[] values, int page, int pageSize) {
        return this.queryByPage(this.clazz, sqlString, values, page, pageSize);
    }

    /**
     * 分页查询，返回分页对象
     *
     * @param entityClass
     * @param sqlString
     * @param values
     * @param page
     * @param pageSize
     * @param <M>
     * @return
     */
    @Override
    public <M> Page<M> queryByPage(Class<M> entityClass, String sqlString, Object[] values, int page, int pageSize) {
        Query query = this.getExecuteQuery(entityClass, sqlString, values);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return new PageImpl<M>(query.getResultList(),
                new PageRequest(page, pageSize),
                getCountBySql(sqlString, values));
    }

    /**
     * 保存或者更新
     *
     * @param entity
     * @return
     */
    @Override
    public T saveOrUpdate(T entity) {
        T tmp = manager.merge(entity);
        manager.flush();
        return tmp;
    }

    /**
     * 查询全部
     *
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        String sql = "from " + this.clazz.getSimpleName();
        Query query = manager.createQuery(sql);
        return query.getResultList();
    }

    /**
     * 根据ID查询实体
     *
     * @param entityClass
     * @param entityId
     * @param <M>
     * @return
     */
    @Override
    public <M> M findOne(Class<M> entityClass, Object entityId) {
        return manager.find(entityClass, entityId);
    }

    /**
     * 查询单个数据
     *
     * @param entityId
     * @return
     */
    @Override
    public T findOne(ID entityId) {
        return findOne(this.clazz, entityId);
    }

    /**
     * 单行删除
     *
     * @param entityClass
     * @param entityId
     * @param <M>
     */
    @Override
    public <M> void delete(Class<M> entityClass, Object entityId) {
        delete(entityClass, new Object[]{entityId});
    }

    /**
     * @param entityClass
     * @param entityIds
     * @param <M>
     */
    @Override
    public <M> void delete(Class<M> entityClass, Object[] entityIds) {
        for (Object id : entityIds) {
            M found = findOne(entityClass, id);
            if (found != null) {
                manager.remove(found);
            } else {
                throw new IllegalArgumentException(entityClass.getName() + " not found: This model ID is " + id);
            }
        }
    }

    /**
     * 单行删除
     *
     * @param entityId
     */
    @Override
    public void delete(ID entityId) {
        this.delete(this.clazz, entityId);
    }

    /**
     * 多行删除
     *
     * @param entityIds
     */
    @Override
    public void delete(ID[] entityIds) {
        this.delete(this.clazz, entityIds);
    }

}
