/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.model.OperationModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 17:26
 */
public interface PrivilegeDao extends BaseDao<PrivilegeEntity, Long> {

    /**
     * 根据app名字获取权限列表
     *
     * @param application
     * @return
     */
    List<PrivilegeEntity> findPrivilegesByAppName(String application);

    /**
     * 根据用户名获取权限列表
     *
     * @param userID
     * @return
     */
    List<PrivilegeEntity> findPrivilegesByUserId(long userID);

    /**
     * 判断是否拥有次权限
     *
     * @param userID,privilegeName
     * @return
     */
    boolean hasPrivilege(long userID, String privilegeName);

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    List<UserEntity> findUsersByPrivName(String privName);

    /**
     * 查询权限列表
     */
    Page<PrivilegeEntity> findByPage(int page, int size, SqlFilter sqlFilter);


    /**
     * 根据权限ID，更新对应操作的关系
     */
    void updateOperations(Long privilegeId, String[] operationIds);


    /**
     * 查找权限ID对应的操作列表
     */
    List<OperationModel> findOperationsByPrivId(Long privilegeId);

}
