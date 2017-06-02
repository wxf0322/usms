/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.GridDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 描述
 *
 * @author Arno Chen
 * @version 1.0
 * @created 2017/5/8 15:58
 */
@Repository
public class GridDaoImpl implements GridDao {
    /**
     *
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridCode
     * @return
     */
    @Override
    public List<UserEntity> getUsersByGridCode(String gridCode) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ug.user_id from usms_user_grid ug where ug.grid_code=:code)");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("code", gridCode);
        return query.getResultList();
    }
}
