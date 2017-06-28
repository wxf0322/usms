/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.ApplicationEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.ApplicationDaoCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述 应用Dao层
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:33
 */
@Repository
public interface ApplicationDao extends JpaRepository<ApplicationEntity, Long>, ApplicationDaoCustom {

    /**
     * 根据ClientId查询应用
     *
     * @param clientId
     * @return
     */
    ApplicationEntity findFirstByClientId(String clientId);

    /**
     * 根据ClientSecret查询应用
     *
     * @param clientSecret
     * @return
     */
    ApplicationEntity findFirstByClientSecret(String clientSecret);

    /**
     * 根据应用编码查询用户列表
     *
     * @param appName
     * @return
     */
    @Query(value = "select * from usms_users u where u.id in " +
            "(select ur.user_id from usms_user_role ur where ur.role_id in " +
            "(select r.id from usms_roles r " +
            "where r.id in (select pr.role_id from usms_privilege_role pr where pr.priv_id in " +
            "(select p.id from usms_privileges p where p.id in " +
            "(select po.oper_id from usms_privilege_operation po " +
            "where po.oper_id in " +
            "(select o.id from usms_operations o where o.application_id in " +
            "(select a.id from usms_applications a where a.name = ?1) " +
            "and o.enabled = 1)) " +
            "and p.enabled = 1)) and r.enabled = 1)) and u.enabled = 1", nativeQuery = true)
    List<UserEntity> listUsersByAppName(String appName);
}
