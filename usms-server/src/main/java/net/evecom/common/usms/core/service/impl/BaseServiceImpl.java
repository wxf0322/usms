/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 描述 Service抽象层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/26 8:53
 */
@Transactional
@Service("baseService")
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

    /**
     * 由业务类实现
     *
     * @return
     */
    public abstract BaseDao<T, ID> getBaseDao();

    /**
     * 保存或者更新
     *
     * @param entity
     * @return
     */
    public T saveOrUpdate(T entity) {
        return getBaseDao().saveOrUpdate(entity);
    }

    /**
     * 查询单个
     *
     * @param entityId
     * @return
     */
    public T findOne(ID entityId) {
        if (entityId == null) return null;
        return getBaseDao().findOne(entityId);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<T> findAll() {
        return getBaseDao().findAll();
    }

    /**
     * 单行删除
     *
     * @param entityId
     */
    public void delete(ID entityId) {
        getBaseDao().delete(entityId);
    }

    /**
     * 多行删除
     *
     * @param entityIds
     */
    public void delete(ID[] entityIds) {
        getBaseDao().delete(entityIds);
    }

}
