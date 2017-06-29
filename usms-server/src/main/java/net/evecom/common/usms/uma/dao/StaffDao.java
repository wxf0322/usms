/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.StaffEntity;
import net.evecom.common.usms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 员工管理Dao
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/26 9:59
 */
@Repository
public interface StaffDao extends JpaRepository<StaffEntity, Long> {

}
