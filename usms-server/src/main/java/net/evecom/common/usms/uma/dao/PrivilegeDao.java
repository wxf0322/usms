/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.PrivilegeDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:26
 */
@Repository
public interface PrivilegeDao extends JpaRepository<PrivilegeEntity, Long>, PrivilegeDaoCustom {

    List<PrivilegeEntity> findByEnabled(Long enabled);

    /**
     * 根据app名字获取权限列表
     *
     * @param appName
     * @return
     */
    @Query(value = "select * from usms_privileges p " +
            "where p.id in (select po.priv_id from usms_privilege_operation po " +
            "where po.oper_id in (select o.id " +
            "from usms_operations o " +
            "where o.application_id in " +
            "(select a.id from usms_applications a " +
            "where a.name= ?1) " +
            "and o.enabled = 1)) ", nativeQuery = true)
    List<PrivilegeEntity> listPrivsByAppName(String appName);

    /**
     * 根据用户名获取权限列表
     *
     * @param userId
     * @return
     */
    @Query(value = "select * from usms_privileges p " +
            "where p.id in( " +
            "select pr.priv_id from usms_privilege_role pr " +
            "where pr.role_id in( " +
            "select ur.role_id from usms_user_role ur " +
            "where ur.user_id = ?1) " +
            ") and p.enabled = 1", nativeQuery = true)
    List<PrivilegeEntity> listPrivsByUserId(long userId);

    /**
     * 判断是否拥有次权限
     *
     * @param userId
     * @param privName
     * @return
     */
    @Query(value = "select * from usms_privileges p " +
            "where p.id in( " +
            "select pr.priv_id from usms_privilege_role pr " +
            "where pr.role_id in( " +
            "select ur.role_id from usms_user_role ur " +
            "where ur.user_id = ?1) " +
            ") and p.enabled = 1 and p.name= ?2", nativeQuery = true)
    List<PrivilegeEntity> listUserPrivileges(long userId, String privName);

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    @Query(value = "select * from usms_users u " +
            "where u.id in (select ur.user_id from usms_user_role ur " +
            "where ur.role_id in (select r.id from usms_roles r " +
            "where r.id in (select pr.role_id from usms_privilege_role pr " +
            "where pr.priv_id in (select p.id from usms_privileges p " +
            "where p.name = ?1)) and r.enabled = 1)) and u.enabled = 1 ", nativeQuery = true)
    List<UserEntity> listUsersByPrivName(String privName);

    /**
     * 获取该权限已选择的角色
     */
    @Query(value = "select * from usms_roles r where r.id in " +
            "(select role_id from usms_privilege_role " +
            "where priv_id =?1) and enabled=1 ", nativeQuery = true)
    List<RoleEntity> listTargetRoles(Long privilegeId);

    /**
     * 获取该权限未选择的角色
     */
    @Query(value = "select * from usms_roles r where r.id not in " +
            "(select role_id from usms_privilege_role " +
            "where priv_id =?1) and enabled=1", nativeQuery = true)
    List<RoleEntity> listSourceRoles(Long privilegeId);

}
