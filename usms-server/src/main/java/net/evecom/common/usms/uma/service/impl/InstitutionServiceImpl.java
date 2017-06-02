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

    @Override
    public BaseDao<InstitutionEntity, Long> getBaseDao() {
        return institutionDao;
    }

    @Override
    public List<InstitutionEntity> findInstByLoginName(String loginName) {
        return institutionDao.findInstByLoginName(loginName);
    }

    @Override
    public InstitutionEntity findByName(String name) {
        return institutionDao.findByName(name);
    }

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByInstName(String instName) {
        return institutionDao.getUsersByInstName(instName);
    }

    @Override
    public List<InstitutionEntity> getInstitutionsByType(Long type) {
        return institutionDao.getInstitutionsByType(type);
    }

}
