/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.custom;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/6/28 下午6:33
 */
public interface UserDaoCustom extends BaseDao<UserEntity> {

    /**
     * @param page          当前页码
     * @param size          页面数据量
     * @param institutionId 组织机构id
     * @return
     */
    Page<UserVO> listUsersByPage(int page, int size, Long institutionId, SqlFilter sqlFilter);

    List<UserEntity> listUsersByLoginNames(String[] loginNames);

    void updateRoles(Long userId, String[] roleIds);

    void updateInstitutions(Long userId, String[] institutionIds);
}
