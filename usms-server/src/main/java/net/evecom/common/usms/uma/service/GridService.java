/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service;

import net.evecom.common.usms.core.vo.TreeData;
import net.evecom.common.usms.core.service.BaseService;
import net.evecom.common.usms.core.util.SqlFilter;
import net.evecom.common.usms.entity.GridEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.vo.UserVO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 15:58
 */
public interface GridService extends BaseService<GridEntity, Long> {

    /**
     * 根据管辖区域编码查询用户列表
     *
     * @param gridCode
     * @return
     */
    List<UserEntity> listUsersByGridCode(String gridCode);

    /**
     * 返回所有的树形数据
     *
     * @return
     */
    List<TreeData> listTreeData();

    /**
     * 返回用户模型分页信息
     *
     * @param page
     * @param size
     * @param gridCode
     * @param sqlFilter
     * @return
     */
    Page<UserVO> listUsersByPage(int page, int size, String gridCode, SqlFilter sqlFilter);

    List<Map<String, Object>> listSourceUsers(Long gridCode, SqlFilter sqlFilter);

    List<Map<String, Object>> listTargetUsers(Long gridCode, SqlFilter sqlFilter);

    void updateUsers(String gridCode, String[] userIds);

    void updateGrids(Long userId, String[] gridCodes);
}
