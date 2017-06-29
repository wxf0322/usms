/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import net.evecom.common.usms.uma.dao.RoleDao;
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.uma.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
     * @see RoleDao
     */
    @Autowired
    private RoleDao roleDao;

    /**
     * @see PrivilegeDao
     */
    @Autowired
    private PrivilegeDao privilegeDao;

    /**
     * @see UserDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 查找所有角色列表
     *
     * @return
     */
    @Override
    public Page<RoleEntity> listRolesByPage(int page, int size, SqlFilter sqlFilter) {
        return roleDao.listRolesByPage(page, size, sqlFilter);
    }

    /**
     * 根据用户ID来查找角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleEntity> listRolesByUserId(long userId) {
        return roleDao.listRolesByUserId(userId);
    }

    /**
     * 判断是否拥有该角色
     *
     * @param userId
     * @param roleName
     * @return
     */
    @Override
    public boolean hasRole(long userId, String roleName) {
        List<RoleEntity> result = roleDao.listUserRoles(userId, roleName);
        return result.size() != 0;
    }

    /**
     * 根据角色编码查询用户列表
     *
     * @param roleName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByRoleName(String roleName) {
        return userDao.listUsersByRoleName(roleName);
    }

    /**
     * 根据角色编码列表查询用户列表
     *
     * @param roleNames
     * @return
     */
    @Override
    public List<UserEntity> listUsersByRoleNames(String[] roleNames) {
        return userDao.listUsersByRoleNames(roleNames);
    }

    @Override
    public List<PrivilegeEntity> listTargetPrivileges(Long roleId) {
        if (roleId == null) {
            return new ArrayList<>();
        } else {
            List<PrivilegeEntity> result = privilegeDao.listTargetPrivileges(roleId);
            return result;
        }
    }

    @Override
    public List<PrivilegeEntity> listSourcePrivileges(Long roleId) {
        if (roleId != null) {
            List<PrivilegeEntity> result = privilegeDao.listSourcePrivileges(roleId);
            return result;
        } else {
            List<PrivilegeEntity> result = privilegeDao.findByEnabled(1L);
            return result;
        }
    }

    @Override
    public void updatePrivileges(Long roleId, String[] privileges) {
        roleDao.updatePrivileges(roleId, privileges);
    }

    /**
     * 根据角色id查找用户列表
     *
     * @param roleId
     * @return
     */
    public List<UserEntity> listUsersByRoleId(Long roleId) {
        return userDao.listUsersByRoleId(roleId);
    }

    /**
     * 根据角色ID查找已选用户列表
     *
     * @param roleId
     * @return
     */
    public List<Map<String, Object>> listTargetUsers(Long roleId, SqlFilter sqlFilter) {
        return roleDao.listTargetUsers(roleId, sqlFilter);
    }

    /**
     * 根据角色ID查找未选用户列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Map<String, Object>> listSourceUsers(Long roleId, Long institutionId, String key) {
        return roleDao.listSourceUsers(roleId, institutionId, key);
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
