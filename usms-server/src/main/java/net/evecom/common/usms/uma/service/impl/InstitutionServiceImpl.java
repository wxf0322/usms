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

    /**
     * 根据登入名查询组织机构
     *
     * @param loginName
     * @return
     */
    @Override
    public List<InstitutionEntity> findInstByLoginName(String loginName) {
        return institutionDao.findInstByLoginName(loginName);
    }

    /**
     * 根据编码查找组织机构
     *
     * @param name
     * @return
     */
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
    public List<UserEntity> findUsersByInstName(String instName) {
        return institutionDao.findUsersByInstName(instName);
    }

    /**
     * 根据组织机构集合查找员工
     *
     * @param instNames
     * @return
     */
    @Override
    public List<UserEntity> findUsersByInstNames(String[] instNames) {
        return institutionDao.findUsersByInstNames(instNames);
    }

    /**
     * 根据类型查找组织机构信息
     *
     * @param type
     * @return
     */
    @Override
    public List<InstitutionEntity> findInstitutionsByType(Long type) {
        return institutionDao.findInstitutionsByType(type);
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
