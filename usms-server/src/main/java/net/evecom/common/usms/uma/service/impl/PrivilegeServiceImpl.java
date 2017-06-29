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
import net.evecom.common.usms.uma.dao.UserDao;
import net.evecom.common.usms.vo.OperationVO;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import net.evecom.common.usms.uma.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:34
 */
@Transactional
@Service
public class PrivilegeServiceImpl extends BaseServiceImpl<PrivilegeEntity, Long>
        implements PrivilegeService {

    /**
     * @see PrivilegeDao
     */
    @Autowired
    private PrivilegeDao privilegeDao;

    /**
     * @see RoleDao
     */
    @Autowired
    private RoleDao roleDao;

    /**
     * @see UserDao
     */
    @Autowired
    private UserDao userDao;

    /**
     * 根据app名称来获取权限列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<PrivilegeEntity> listPrivsByAppName(String appName) {
        return privilegeDao.listPrivsByAppName(appName);
    }

    /**
     * 根据用户编码来获取权限列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<PrivilegeEntity> listPrivsByUserId(long userId) {
        return privilegeDao.listPrivsByUserId(userId);
    }

    /**
     * 判断是否拥有该权限
     *
     * @param userId
     * @param privName
     * @return
     */
    @Override
    public boolean hasPrivilege(long userId, String privName) {
        List<PrivilegeEntity> result = privilegeDao.listUserPrivileges(userId, privName);
        return result.size() != 0;
    }

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByPrivName(String privName) {
        return userDao.listUsersByPrivName(privName);
    }

    /**
     * 查询权限列表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<PrivilegeEntity> listPrivsByPage(int page, int size, SqlFilter sqlFilter) {
        return privilegeDao.listPrivsByPage(page, size, sqlFilter);
    }


    @Override
    public void updateOperations(Long privilegeId, String[] operationIds) {
        privilegeDao.updateOperations(privilegeId, operationIds);
    }

    @Override
    public List<OperationVO> listOpersByPrivId(Long privilegeId) {
        return privilegeDao.listOpersByPrivId(privilegeId);
    }

    @Override
    public List<RoleEntity> listTargetRoles(Long privilegeId) {
        if (privilegeId == null) {
            return new ArrayList<>();
        } else {
            return roleDao.listTargetRolesByPrivId(privilegeId);
        }
    }

    @Override
    public List<RoleEntity> listSourceRoles(Long privilegeId) {
        if (privilegeId != null) {
            return roleDao.listSourceRolesByPrivId(privilegeId);
        } else {
            return roleDao.findByEnabled(1L);
        }
    }

    @Override
    public void updateRoles(Long privilegeId, String[] roleIds) {
        privilegeDao.updateRoles(privilegeId, roleIds);
    }

}
