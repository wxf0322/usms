/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.entity.*;
import net.evecom.common.usms.model.UserModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 描述 UserDao
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/4/24 15:46
 */
public interface UserDao extends BaseDao<UserEntity, Long> {

    /**
     * @param page 当前页码
     * @param size 页面数据量
     * @return
     */
    Page<UserModel> findModelsByPage(int page, int size);

    UserEntity findByLoginName(String loginName);

    List<RoleEntity> findRolesById(Long id);

    List<OperationEntity> findOperationsById(Long id);

    List<ApplicationEntity> findApplicationsById(Long id);

    List<GridEntity> findGridsByLoginName(String loginName);

    void updateInstitutions(Long userId,String[] institutionIds);

    List<InstitutionEntity> findInstByUserId(Long userId);

}
