/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.oauth2.dao.impl;

import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.oauth2.dao.StaffDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 10:21
 */
@Repository
public class StaffDaoImpl implements StaffDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    @Override
    public StaffEntity findOne(Long id) {
        return manager.find(StaffEntity.class, id);
    }
}
