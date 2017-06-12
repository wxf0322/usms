/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 18:28
 */
public interface RoleService extends BaseService<RoleEntity, Long> {

    /**
     * 根据用户ID来查找角色列表
     *
     * @param userId
     * @return
     */
    List<RoleEntity> findRolesByUserId(long userId);

    /**
     * 判断是否拥有该角色
     *
     * @param userId
     * @param roleName
     * @return
     */
    boolean hasRole(long userId, String roleName);

    /**
     * 根据角色编码查询用户列表
     *
     * @param roleName
     * @return
     */
    List<UserEntity> findUsersByRoleName(String roleName);

    /**
     * 根据权限角色查询列表用户列表
     *
     * @param roleNames
     * @return
     */
    List<UserEntity> findUsersByRoleNames(String[] roleNames);

    /**
     * 查询所有角色列表
     *
     * @param page
     * @param size
     * @param sqlFilter
     * @return
     */
    Page<RoleEntity> findByPage(int page, int size, SqlFilter sqlFilter);


    /**
     * 查找角色Id对应的权限列表
     */
    List<PrivilegeEntity> getSelectedPrivileges(Long roleId);

    /**
     * 查找角色Id对应的未选择权限列表
     */
    List<PrivilegeEntity> getUnselectedPrivileges(Long roleId);

    /**
     * 更新角色对应的权限列表
     */
    void updatePrivileges(Long roleId, String[] privileges);

    /**
     * 根据角色id查找用户列表
     */
    List<UserEntity> findUsersByRoleId(Long roleId);

    /**
     * 根据角色ID查找已选用户列表
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> getSelectedUsers(Long roleId);

    /**
     * 根据角色ID查找未选用户列表
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> getUnselectedUsers(Long roleId);

    /**
     * 更新用户列表
     *
     * @param roleId
     * @param userIds
     */
    void updateUsers(Long roleId, String[] userIds);
}
