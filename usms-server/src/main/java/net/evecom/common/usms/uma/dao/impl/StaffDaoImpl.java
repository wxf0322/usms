/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.StaffDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 10:21
 */
@Repository
public class StaffDaoImpl extends BaseDaoImpl<StaffEntity, Long>
        implements StaffDao {

    /**
     * 注入实体管理器
     */
    @PersistenceContext
    private EntityManager manager;

    /**
     * 描述
     * 查询网格员列表
     * @return
     * @param officalPost
     */
    @Override
    public List<UserEntity> findUsersByOfficalPost(String officalPost) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_users u where u.staff_id in\n")
                .append(" (select s.id from usms_staffs s where s.offical_post = :officalPost)\n")
                .append("  and u.enabled=1");
        String sql = sb.toString();
        Query query = manager.createNativeQuery(sql, UserEntity.class);
        query.setParameter("officalPost", officalPost);
        return query.getResultList();
    }

}
