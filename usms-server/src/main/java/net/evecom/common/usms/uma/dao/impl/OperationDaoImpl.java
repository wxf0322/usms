/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.entity.OperationEntity;

import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.OperationDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作相关实现类
 *
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 14:20
 */
@Repository
public class OperationDaoImpl extends BaseDaoImpl<OperationEntity, Long>
        implements OperationDao {

    /**
     * 根据app名称获取操作列表
     *
     * @param appName
     * @return
     */
    @Override
    public List<OperationEntity> listOpersByAppName(String appName) {
        List<OperationEntity> result = super.namedQueryForClass("Operation.listOpersByAppName",
                new Object[]{appName});
        return result;
    }

    /**
     * 根据操作编码查询用户列表
     *
     * @param operName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByOperName(String operName) {
        List<UserEntity> result = super.namedQueryForClass("Operation.listUsersByOperName",
                new Object[]{operName});
        return result;
    }

    /**
     * 判断是否拥有该操作
     *
     * @param userId
     * @param operName
     * @return boolean
     */
    @Override
    public boolean hasOperation(long userId, String operName) {
        List results = super.namedQueryForClass("Operation.hasOperation",
                new Object[]{userId, operName});
        return results.size() != 0;
    }
}
