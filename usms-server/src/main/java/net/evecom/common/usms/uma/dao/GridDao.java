/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.GridDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 15:58
 */
@Repository
public interface GridDao extends JpaRepository<GridEntity, Long>, GridDaoCustom {

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridCode
     * @return
     */
    @Query(value = "select * from usms_users u where u.id in " +
            "(select ug.user_id from usms_user_grid ug where ug.grid_code=?1)",
            nativeQuery = true)
    List<UserEntity> listUsersByGridCode(String gridCode);

}
