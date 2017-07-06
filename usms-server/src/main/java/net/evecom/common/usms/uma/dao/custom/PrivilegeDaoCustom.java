/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.custom;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.PrivilegeEntity;
import net.evecom.common.usms.vo.OperationVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午6:08
 */
public interface PrivilegeDaoCustom extends BaseDao<PrivilegeEntity> {

    /**
     * 查询权限列表
     *
     * @param page
     * @param size
     * @param sqlFilter
     * @return
     */
    Page<PrivilegeEntity> listPrivsByPage(int page, int size, SqlFilter sqlFilter);

    /**
     * 根据权限ID，更新对应操作的关系
     *
     * @param privilegeId
     * @param operationIds
     */
    void updateOperations(Long privilegeId, String[] operationIds);

    /**
     * 查找权限ID对应的操作列表
     *
     * @param privilegeId
     * @return
     */
    List<OperationVO> listOpersByPrivId(Long privilegeId);

    /**
     * 更新权限与用户之间的关系
     *
     * @param privilegeId
     * @param roleIds
     */
    void updateRoles(Long privilegeId, String[] roleIds);

}
