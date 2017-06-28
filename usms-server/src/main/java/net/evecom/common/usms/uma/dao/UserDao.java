/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.uma.dao.custom.UserDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午6:33
 */
@Repository
public interface UserDao extends JpaRepository<UserEntity, Long>, UserDaoCustom {

    UserEntity findFirstByLoginName(String loginName);


    @Query(value = "select * from usms_roles r where r.id in " +
            "(select ur.role_id from usms_user_role ur where ur.user_id = ?1) " +
            "and r.enabled = 1 ", nativeQuery = true)
    List<RoleEntity> listRolesByUserId(Long userId);

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

    @Query(value = "select * from usms_applications a where a.id in ( " +
            "select distinct o.application_id from usms_operations o " +
            "where o.id in (select po.oper_id " +
            "from usms_privilege_operation po " +
            "where po.priv_id in " +
            "(select p.id from usms_privileges p " +
            "where p.id in (select pr.priv_id " +
            "from usms_privilege_role pr, usms_roles r " +
            "where pr.role_id in " +
            "(select ur.role_id from usms_user_role ur where ur.user_id = ?1) " +
            "and pr.role_id = r.id and r.enabled = 1) " +
            " and p.enabled = 1)) and o.enabled = 1 and o.application_id is not null) ", nativeQuery = true)
    List<ApplicationEntity> listAppsByUserId(Long userId);

    @Query(value = " select * from gsmp_loc_grids g " +
            "where g.code in " +
            "(select ug.grid_code from usms_user_grid ug " +
            "where ug.user_id = " +
            "(select u.id from usms_users u where u.login_name = ?1)) ", nativeQuery = true)
    List<GridEntity> listGridsByLoginName(String loginName);


    @Query(value = "select * from usms_institutions i where i.id in " +
            "(select ui.institution_id from usms_user_institution ui where ui.user_id = ?1) ", nativeQuery = true)
    List<InstitutionEntity> listInstsByUserId(Long userId);

    @Query(value = "select * from usms_roles where id in " +
            "(select role_id from usms_user_role where user_id = ?1) and enabled=1 ", nativeQuery = true)
    List<RoleEntity> listTargetRoles(Long userId);

    @Query(value = "select * from usms_roles where id not in " +
            "(select role_id from usms_user_role where user_id = ?1) and enabled=1 ", nativeQuery = true)
    List<RoleEntity> listSourceRoles(Long userId);

}
