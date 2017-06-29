/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.JpaUtil;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.InstitutionDaoCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午5:27
 */
@Repository
public class InstitutionDaoImpl extends BaseDaoImpl<InstitutionEntity> implements InstitutionDaoCustom {

    @Override
    public boolean canBeDeleted(Long id) {
        String sql = "select i.id from usms_institutions i where i.parent_id = ?";
        List<Object> children = super.queryForObject(sql, new Object[]{id});
        if (children != null && children.size() > 0) return false;
        sql = "select ui.user_id from usms_user_institution ui where ui.institution_id = ?";
        List<Object> users = super.queryForObject(sql, new Object[]{id});
        if (users != null && users.size() > 0) return false;
        else return true;
    }

}
