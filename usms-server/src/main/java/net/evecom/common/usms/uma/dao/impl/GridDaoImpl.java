/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao.impl;

import net.evecom.common.usms.core.dao.impl.BaseDaoImpl;
import net.evecom.common.usms.core.model.TreeDataModel;
import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.GridDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述 管辖区域管理相关Dao层
 *
 * @author Arno Chen
 * @version 1.0
 * @created 2017/5/8 15:58
 */
@Repository
public class GridDaoImpl extends BaseDaoImpl<GridEntity, Long>
        implements GridDao {

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridCode
     * @return
     */
    @Override
    public List<UserEntity> findUsersByGridCode(String gridCode) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from usms_users u where u.id in\n")
                .append(" (select ug.user_id from usms_user_grid ug where ug.grid_code=?)");
        String sql = sb.toString();
        return super.queryBySql(UserEntity.class, sql, new Object[]{gridCode});
    }

    /**
     * 查询所有网格树形节点
     *
     * @return
     */
    @Override
    public List<TreeDataModel> findTreeData() {
        String sql = "select id, name, code, parent_id from gsmp_loc_grids";
        List<Map<String, Object>> variables = super.queryMap(sql, null);
        List<TreeDataModel> result = new ArrayList<>();
        for (Map<String, Object> variable : variables) {
            TreeDataModel treeData = new TreeDataModel();
            treeData.setId(((BigDecimal) variable.get("ID")).longValue());
            Object parentId = variable.get("PARENT_ID");
            if (parentId != null) {
                treeData.setParentId(((BigDecimal) parentId).longValue());
            } else {
                treeData.setParentId(0L);
            }
            treeData.setLabel((String) variable.get("NAME"));

            // 设置树节点数据
            Map<String, Object> data = new HashMap<>();
            String code = (String) variable.get("CODE");
            String descripiton = (String) variable.get("DESCRIPITON");
            String dutyPhone = (String) variable.get("DUTY_PHONE");
            data.put("code", code);
            data.put("descripiton", descripiton);
            data.put("dutyPhone", dutyPhone);

            treeData.setData(data);
            result.add(treeData);
        }
        return result;
    }

}
