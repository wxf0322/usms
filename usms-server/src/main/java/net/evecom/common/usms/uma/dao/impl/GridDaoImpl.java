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
     * @param gridName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByGridName(String gridName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u\n")
                .append(" where u.id in \n" )
                .append(" (select * from ur.id from usms_user_role ur\n")
                .append(" where ur.role_id in (")
                .append(" select r.id from usms_roles r where r.id in\n")
                .append(" (select rg.role_id from usms_role_grid rg\n")
                .append(" where rg.grid_id =\n")
                .append(" (select g.id from gsmp_loc_grids g where g.name = :name))\n")
                .append(" and r.enabled = 1)) and u.enabled = 1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("name", gridName);
        return query.getResultList();
    }
}
