/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.RoleDao;
import net.evecom.common.usms.uma.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 18:29
 */
@Transactional
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity, Long>
        implements RoleService {

    /**
     * roleDao的注入
     */
    @Autowired
    private RoleDao roleDao;

    @Override
    public BaseDao<RoleEntity, Long> getBaseDao() {
        return roleDao;
    }

    /**
     * 查找所有角色列表
     *
     * @return
     */
    @Override
    public Page<RoleEntity> findByPage(int page, int size, SqlFilter sqlFilter) {
        return roleDao.findByPage(page, size,sqlFilter);
    }

    /**
     * 根据用户ID来查找角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleEntity> findRolesByUserId(long userId) {
        return roleDao.findRolesByUserId(userId);
    }

    /**
     * 判断是否拥有该角色
     *
     * @param userID
     * @param roleName
     * @return
     */
    @Override
    public boolean hasRole(long userId, String roleName) {
        return roleDao.hasRole(userId, roleName);
    }

    /**
     * 根据角色编码查询用户列表
     *
     * @param roleName
     * @return
     */
    @Override
    public List<UserEntity> findUsersByRoleName(String roleName) {
        return roleDao.findUsersByRoleName(roleName);
    }

    /**
     * 根据角色编码列表查询用户列表
     *
     * @param roleNames
     * @return
     */
    @Override
    public List<UserEntity> findUsersByRoleNames(String[] roleNames) {
        return roleDao.findUsersByRoleNames(roleNames);
    }

    @Override
    public List<PrivilegeEntity> getSelectedPrivileges(Long roleId) {
        return roleDao.getSelectedPrivileges(roleId);
    }

    @Override
    public List<PrivilegeEntity> getUnselectedPrivileges(Long roleId) {
        return roleDao.getUnselectedPrivileges(roleId);
    }

    @Override
    public void updatePrivileges(Long roleId, String[] privileges) {
        roleDao.updatePrivileges(roleId, privileges);
    }

    /**
     * 根据角色id查找用户列表
     */
    public List<UserEntity> findUsersByRoleId(Long roleId) {
        return roleDao.findUsersByRoleId(roleId);
    }

    /**
     * 根据角色ID查找已选用户列表
     *
     * @param roleId
     * @return
     */
    public List<Map<String, Object>> getSelectedUsers(Long roleId) {
        return roleDao.getSelectedUsers(roleId);
    }

    /**
     * 根据角色ID查找未选用户列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Map<String, Object>> getUnselectedUsers(Long roleId) {
        return roleDao.getUnselectedUsers(roleId);
    }

    /**
     * 更新用户列表
     *
     * @param roleId
     * @param users
     */
    @Override
    public void updateUsers(Long roleId, String[] users) {
        roleDao.updateUsers(roleId, users);
    }

}
