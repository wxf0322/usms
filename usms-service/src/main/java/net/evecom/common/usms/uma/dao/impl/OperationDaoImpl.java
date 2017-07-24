/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.util.MapUtil;
import net.evecom.common.usms.entity.OperationEntity;
import net.evecom.common.usms.uma.dao.custom.OperationDaoCustom;
import net.evecom.common.usms.vo.OperationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/7/20 上午9:29
 */
@Repository
public class OperationDaoImpl extends BaseDaoImpl<OperationEntity> implements OperationDaoCustom {

    /**
     * @see Logger
     */
    private static Logger logger = LoggerFactory.getLogger(OperationDaoImpl.class);

    @Override
    public boolean canBeDeleted(Long id) {
        String sql = "select o.id from usms_operations o where o.parent_id = ?";
        List<Object> children = super.queryForObject(sql, new Object[]{id});
        if (children != null && children.size() > 0)
            return false;
        else
            return true;
    }


    @Override
    public List<OperationVO> listOpersByPrivId(Long privilegeId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from usms_operations where id in( ")
                .append("select oper_id from usms_privilege_operation t ")
                .append("where priv_id=?) and enabled=1");
        String sql = sb.toString();
        List<Map<String, Object>> rows = super.queryForMap(sql, new Object[]{privilegeId});
        List<OperationVO> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            try {
                Map<String, Object> camelMap = MapUtil.toCamelCaseMap(row);
                OperationVO operation = MapUtil.toObject(OperationVO.class ,camelMap);
                result.add(operation);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
