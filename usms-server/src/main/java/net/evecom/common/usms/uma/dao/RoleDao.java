/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.custom.RoleDaoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 18:24
 */
@Repository
public interface RoleDao extends JpaRepository<RoleEntity, Long>, RoleDaoCustom {

    /**
     *
     * @param enabled
     * @return
     */
    List<RoleEntity> findByEnabled(Long enabled);

    /**
     * 根据用户名查找角色列表
     *
     * @param userId
     * @return
     */
    @Query(value = "select * from usms_roles r where r.id in( " +
            "select ur.role_id from usms_user_role ur " +
            "where ur.user_id = ?1) and r.enabled = 1", nativeQuery = true)
    List<RoleEntity> listRolesByUserId(long userId);

    /**
     * 判断是否拥有此角色
     *
     * @param userId
     * @return
     */
    @Query(value = "select * from usms_roles r where r.id in( " +
            "select ur.role_id from usms_user_role ur " +
            "where ur.user_id = ?1) and r.enabled=1 and r.name= ?2", nativeQuery = true)
    List<RoleEntity> listUserRoles(long userId, String roleName);

    /**
     * 根据权限角色查询用户列表
     *
     * @param roleName
     * @return
     */
    @Query(value = "select * from usms_users u where u.id in " +
            "(select ur.user_id from usms_user_role ur where ur.role_id in " +
            "(select r.id from usms_roles r where r.name = ?1 and r.enabled = 1) " +
            ")and u.enabled = 1", nativeQuery = true)
    List<UserEntity> listUsersByRoleName(String roleName);

    /**
     * 查找角色Id对应的权限列表
     *
     * @param roleId
     * @return
     */
    @Query(value = " select * from usms_privileges where id in( " +
            "select priv_id from usms_privilege_role t " +
            "where role_id=?1) and enabled = 1", nativeQuery = true)
    List<PrivilegeEntity> listTargetPrivileges(Long roleId);

    /**
     * 查找角色Id对应的未选择权限列表
     *
     * @param roleId
     * @return
     */
    @Query(value = "select * from usms_privileges where id not in( " +
            "select priv_id from usms_privilege_role t " +
            "where role_id=?1) and enabled = 1", nativeQuery = true)
    List<PrivilegeEntity> listSourcePrivileges(Long roleId);

    /**
     * 根据角色id查找用户列表
     *
     * @param roleId
     * @return
     */
    @Query(value = "select * from usms_users u where u.id in " +
            "(select ur.user_id from usms_user_role ur " +
            "where ur.role_id = ?1)", nativeQuery = true)
    List<UserEntity> listUsersByRoleId(Long roleId);

}
