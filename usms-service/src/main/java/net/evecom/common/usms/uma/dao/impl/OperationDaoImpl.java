/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.uma.dao.custom.OperationDaoCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/7/20 上午9:29
 */
@Repository
public class OperationDaoImpl extends BaseDaoImpl<OperationEntity> implements OperationDaoCustom {

    @Override
    public boolean canBeDeleted(Long id) {
        String sql = "select o.id from usms_operations o where o.parent_id = ?";
        List<Object> children = super.queryForObject(sql, new Object[]{id});
        if (children != null && children.size() > 0)
            return false;
        else
            return true;
    }
}
