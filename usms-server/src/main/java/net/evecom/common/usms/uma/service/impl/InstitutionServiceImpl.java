/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.core.dao.BaseDao;
import net.evecom.common.usms.core.service.impl.BaseServiceImpl;
import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.InstitutionDao;
import net.evecom.common.usms.uma.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 15:16
 */
@Transactional
@Service
public class InstitutionServiceImpl extends BaseServiceImpl<InstitutionEntity, Long>
        implements InstitutionService {

    /**
     * 注入InstitutionDao
     */
    @Autowired
    private InstitutionDao institutionDao;

    /**
     * 根据登入名查询组织机构
     *
     * @param loginName
     * @return
     */
    @Override
    public List<InstitutionEntity> listInstsByLoginName(String loginName) {
        return institutionDao.listInstsByLoginName(loginName);
    }

    /**
     * 根据编码查找组织机构
     *
     * @param name
     * @return
     */
    @Override
    public InstitutionEntity getInstByInstName(String instName) {
        return institutionDao.findFirstByName(instName);
    }

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    @Override
    public List<UserEntity> listUsersByInstName(String instName) {
        return institutionDao.listUsersByInstName(instName);
    }

    /**
     * 根据组织机构集合查找员工
     *
     * @param instNames
     * @return
     */
    @Override
    public List<UserEntity> listUsersByInstNames(String[] instNames) {
        return institutionDao.listUsersByInstNames(instNames);
    }

    /**
     * 检查是否能被删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean canBeDeleted(Long id) {
        return institutionDao.canBeDeleted(id);
    }

}
