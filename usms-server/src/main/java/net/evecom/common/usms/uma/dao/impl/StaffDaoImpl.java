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
     * 描述
     * 查询网格员列表
     * @return
     * @param officalPost
     */
    @Override
    public List<UserEntity> listUsersByOfficalPost(String officalPost) {
        List<UserEntity> result = super.namedQueryForClass("Staff.listUsersByOfficalPost",
                new Object[]{officalPost});
        return result;
    }

}
