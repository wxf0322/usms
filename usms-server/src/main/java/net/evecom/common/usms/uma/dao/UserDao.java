/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述 UserDao
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:46
 */
public interface UserDao extends BaseDao<UserEntity, Long> {

    /**
     * @param page          当前页码
     * @param size          页面数据量
     * @param institutionId 组织机构id
     * @return
     */
    Page<UserVO> listUsersByPage(int page, int size, Long institutionId, SqlFilter sqlFilter);

    UserEntity getUserByLoginName(String loginName);

    List<UserEntity> listUsersByLoginNames(String[] loginNames);

    List<RoleEntity> listRolesByUserId(Long userId);

    List<OperationEntity> listOpersByUserId(Long userId);

    List<ApplicationEntity> listAppsByUserId(Long userId);

    List<GridEntity> listGridsByLoginName(String loginName);

    void updateInstitutions(Long userId, String[] institutionIds);

    List<InstitutionEntity> listInstsByUserId(Long userId);

    List<RoleEntity> listTargetRoles(Long userId);

    List<RoleEntity> listSourceRoles(Long userId);

    void updateRoles(Long userId, String[] roleIds);

}
