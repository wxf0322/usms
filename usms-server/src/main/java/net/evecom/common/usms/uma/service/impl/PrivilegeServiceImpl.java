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
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.OperationModel;
import net.evecom.common.usms.uma.dao.PrivilegeDao;
import net.evecom.common.usms.uma.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * privilegeDao的注入
     */
    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public BaseDao<PrivilegeEntity, Long> getBaseDao() {
        return privilegeDao;
    }

    /**
     * 根据app名称来获取权限列表
     *
     * @param application
     * @return
     */
    @Override
    public List<PrivilegeEntity> getPrivilegesByAppName(String application) {
        return privilegeDao.getPrivilegesByAppName(application);
    }

    /**
     * 根据用户编码来获取权限列表
     *
     * @param userID
     * @return
     */
    @Override
    public List<PrivilegeEntity> getPrivilegesByUserId(long userID) {
        return privilegeDao.getPrivilegesByUserId(userID);
    }

    /**
     * 判断是否拥有该权限
     *
     * @param userID,privilegeName
     * @return
     */
    @Override
    public boolean hasPrivilege(long userID, String privilegeName) {
        return privilegeDao.hasPrivilege(userID, privilegeName);
    }

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByPrivName(String privName) {
        return privilegeDao.getUsersByPrivName(privName);
    }

    /**
     * 查询权限列表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<PrivilegeEntity> findByPage(int page, int size, SqlFilter sqlFilter) {
        return privilegeDao.findByPage(page, size,sqlFilter);
    }



    @Override
    public void updateOperations(Long privilegeId, String[] operationIds) {
           privilegeDao.updateOperations(privilegeId,operationIds);
    }

    @Override
    public List<OperationModel> findOperationsByPrivId(Long privilegeId) {
        return privilegeDao.findOperationsByPrivId(privilegeId);
    }

}
