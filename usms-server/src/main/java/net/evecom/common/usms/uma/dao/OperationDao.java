/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:20
 */
@Repository
public interface OperationDao extends JpaRepository<OperationEntity, Long> {

    /**
     * 获取应用操作列表
     *
     * @param appName
     * @return
     */
    @Query(value = "select * from usms_operations o " +
            "where o.application_id in (select a.id from usms_applications a " +
            "where a.name = ?1) and o.enabled = 1"
            , nativeQuery = true)
    List<OperationEntity> listOpersByAppName(String appName);

    @Query(value = "select * from usms_operations o where o.id in " +
            "(select po.oper_id from usms_privilege_operation po " +
            " where po.priv_id in " +
            "(select p.id from usms_privileges p where p.id in " +
            "(select pr.priv_id from usms_privilege_role pr, usms_roles r " +
            "where pr.role_id in " +
            "(select ur.role_id from usms_user_role ur where ur.user_id = ?1) " +
            "and pr.role_id = r.id and r.enabled = 1) " +
            "and p.enabled = 1)) " +
            "and o.enabled = 1", nativeQuery = true)
    List<OperationEntity> listOpersByUserId(Long userId);


    /**
     * 判断是否拥有该操作
     *
     * @param userId
     * @param operationName
     * @return
     */
    @Query(value = "select * from usms_operations p where p.id in( " +
            "select po.oper_id from usms_privilege_operation po where po.priv_id in( " +
            "select pr.priv_id from usms_privilege_role pr where pr.role_id in( " +
            "select ur.role_id from usms_user_role ur where ur.user_id = ?1))) " +
            "and p.enabled=1 and p.name= ?2", nativeQuery = true)
    List<OperationEntity> listUserOperations(long userId, String operName);

}
