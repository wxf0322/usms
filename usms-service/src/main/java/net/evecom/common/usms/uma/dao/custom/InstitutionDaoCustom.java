/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.custom;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.entity.InstitutionEntity;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午5:25
 */
public interface InstitutionDaoCustom extends BaseDao<InstitutionEntity> {

    /**
     * 检查是否能被删除
     *
     * @param id
     * @return
     */
    boolean canBeDeleted(Long id);
}
