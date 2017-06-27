/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.vo.OperationVO;
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
     * @param appName
     * @return
     */
    List<PrivilegeEntity> listPrivsByAppName(String appName);

    /**
     * 根据用户名获取权限列表
     *
     * @param userId
     * @return
     */
    List<PrivilegeEntity> listPrivsByUserId(long userId);

    /**
     * 判断是否拥有次权限
     *
     * @param userId
     * @param
     * @return
     */
    boolean hasPrivilege(long userId, String privName);

    /**
     * 根据权限编码查询用户列表
     *
     * @param privName
     * @return
     */
    List<UserEntity> listUsersByPrivName(String privName);

    /**
     * 查询权限列表
     */
    Page<PrivilegeEntity> listPrivsByPage(int page, int size, SqlFilter sqlFilter);


    /**
     * 根据权限ID，更新对应操作的关系
     */
    void updateOperations(Long privilegeId, String[] operationIds);


    /**
     * 查找权限ID对应的操作列表
     */
    List<OperationVO> listOpersByPrivId(Long privilegeId);

    /**
     * 获取该权限已选择的角色
     */
    List<RoleEntity> listTargetRoles(Long privilegeId );

    /**
     * 获取该权限未选择的角色
     */
    List<RoleEntity> listSourceRoles(Long privilegeId);


    void updateRoles(Long privilegeId, String[] roleIds);
}
